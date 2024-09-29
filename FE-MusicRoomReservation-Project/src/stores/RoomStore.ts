import { defineStore } from "pinia";
import { fetchData } from "@/fetchUtils/fetch";

export const useRoomStore = defineStore("RoomStore", {
  state: () => ({
    rooms: [] as Room[],
    bookedRoom: null as BookedRoom | null, // Initialize to null
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
  },

  actions: {
    async loadRooms(): Promise<void> {
      try {
        const data: Room[] = await fetchData("http://localhost:8080/roomTypes");
        this.rooms = data;
      } catch (error) {
        console.error("Failed to fetch rooms:", error);
      }
    },
    setSelectedRoom(selectedRoom: BookedRoom): void {
      this.bookedRoom = selectedRoom;
      console.log("Selected room:", this.bookedRoom);
    },
  },
  persist: true,
});
