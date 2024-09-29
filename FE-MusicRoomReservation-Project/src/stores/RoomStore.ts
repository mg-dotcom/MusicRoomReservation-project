import { defineStore } from "pinia";
import { fetchData } from "@/fetchUtils/fetch";

export const useRoomStore = defineStore("RoomStore", {
  state: () => ({
    rooms: [] as Room[],
    selectedRoom: {} as RoomAndTime | {},
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
    getSelectedRoom: (state) => state.selectedRoom,
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
    setSelectedRoom(room: RoomAndTime): void {
      this.selectedRoom = room;
    },
  },
});
