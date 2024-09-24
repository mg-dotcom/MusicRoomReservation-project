import { defineStore } from "pinia";
import { fetchData } from "@/fetchUtils/fetch";

export const useRoomStore = defineStore("RoomStore", {
  state: () => ({
    rooms: [] as Room[], // Directly initialize as an array of Room
  }),

  getters: {
    getRooms: (state) => state.rooms, // Simplified getter
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
    selectRoomType(roomType: string): void {
      // Find the room with the given roomType and set it as selected
    },
  },
});
