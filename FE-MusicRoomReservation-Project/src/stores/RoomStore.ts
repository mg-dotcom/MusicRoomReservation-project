import { defineStore } from "pinia";
import { fetchData } from "@/fetchUtils/fetch";
import Swal from "sweetalert2";
const apiUrl = import.meta.env.VITE_BASE_URL as string; // Removed because process.env is not available in the browser environment

export const useRoomStore = defineStore("RoomStore", {
  state: () => ({
    rooms: [] as Room[],
    bookedRoom: null as BookedRoom | null, // Initialize to null
    roomReservation: {} as { [roomId: string]: Reservation[] }, // Initialize with type
  }),

  getters: {
    getRooms: (state) => state.rooms,
    getMergeRooms: (state) => {
      return Object.values(state.rooms)
        .flat()
        .map((room) => ({
          roomId: room.roomId,
          name: room.name,
          building: room.building,
          instruments: room.instruments,
          capacity: room.capacity,
          features: room.features,
          imageUrl: room.imageUrl || null,
          roomType: room.roomType,
        }));
    },
    getBookedRoom: (state) => state.bookedRoom,
    getRoomReservation: (state) => state.roomReservation,
  },

  actions: {
    async loadRooms(): Promise<void> {
      try {
        const data: Room[] = await fetchData(`${apiUrl}/roomTypes`);
        this.rooms = data;
      } catch (error) {
        console.error("Failed to fetch rooms:", error);
      }
    },
    setSelectedRoom(selectedRoom: BookedRoom): void {
      this.bookedRoom = selectedRoom;
    },
    reserveRoom(userReserve: Reservation & { roomId: string }): void {
      const { roomId, time, name, tel } = userReserve;

      if (!this.roomReservation[roomId]) {
        this.roomReservation[roomId] = [];
      }

      const existingReservation = this.roomReservation[roomId].find(
        (reservation) => reservation.time === time
      );

      if (!existingReservation) {
        this.roomReservation[roomId].push({ time, name, tel });
      } else {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: `Time ${time} is already reserved for room ${roomId}.`,
        });
      }
    },

    getReservations(roomId: string): Reservation[] {
      return this.roomReservation[roomId] || [];
    },
  },
  persist: true,
});
