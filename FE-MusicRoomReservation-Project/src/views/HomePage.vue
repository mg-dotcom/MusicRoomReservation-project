<script setup>
import { onMounted, ref } from "vue";
import { useRoomStore } from "@/stores/RoomStore";

import SearchButton from "@/components/SearchButton.vue";
import { capitalizeAndSpace } from "@/libsUtils.js";

const roomStore = useRoomStore();
const roomKeys = ref([]);

const timeSlots = ref([
  "08:30 - 10:20",
  "10:30 - 12:20",
  "12:30 - 14:20",
  "14:30 - 16:20",
]);

onMounted(async () => {
  await roomStore.loadRooms();
  roomKeys.value = Object.keys(roomStore.getRooms).map((key) => {
    return {
      roomType: key,
      rooms: roomStore.getRooms[key],
    };
  });
});
</script>

<template>
  <div class="w-full px-14 py-11">
    <div class="section-filter">
      <h1 class="text-3xl font-semibold">Room Reservation</h1>
      <div class="filter flex my-4 justify-between">
        <div class="flex flex-col text-lg">
          <div
            class="select-filter flex space-x-3 w-full justify-center items-center"
          >
            <div class="filter-room font-medium">
              <select id="room" class="p-1">
                <option value="" disabled selected hidden>Room</option>
                <option
                  v-for="room in roomStore.getRooms"
                  :key="room.roomId"
                  :value="room.roomId"
                >
                  {{ room.name }}
                </option>
              </select>
            </div>
            <div class="filter-count font-medium">
              <select id="count" class="p-1">
                <option value="" disabled selected hidden>Count</option>
                <option v-for="n in 10" :key="n" :value="n">{{ n }}</option>
              </select>
            </div>
            <div class="filter-instrument font-medium">
              <select id="instrument" class="p-1">
                <option value="" disabled selected hidden>
                  Music instrument
                </option>
                <option
                  v-for="instrument in roomStore.getInstruments"
                  :key="instrument"
                  :value="instrument"
                >
                  {{ instrument }}
                </option>
              </select>
            </div>
            <SearchButton />
          </div>
          <div
            class="my-2 text-sm text-slate-300 hover:text-slate-400 transition cursor-pointer"
          >
            Clear All Filter
          </div>
        </div>
        <div class="search-filter relative">
          <input
            type="text"
            placeholder="Search"
            class="search-input h-9 w-64 px-2 focus:ring-2 focus:ring-primary focus:outline-none rounded-l-md"
          />
          <button
            class="clear-search-filter absolute right-9 translate-y-1.5 text-slate-300 hover:text-slate-400 hover:bg-slate-100 bg-white p-0.5 rounded-full z-10"
          >
            <svg
              class="w-4 h-4"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
          <button
            class="search-filter-submit absolute top-0 right-0 h-9 px-2 rounded-r-md rounded-l-none bg-primary text-white hover:bg-primary-dark"
          >
            <svg
              class="w-4 h-4"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 20 20"
            >
              <path
                stroke="currentColor"
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
              />
            </svg>
          </button>
        </div>
      </div>
    </div>

    <div class="section-room-types flex gap-x-3 mt-11 text-xl relative z-0">
      <div
        class="all-rooms px-7 py-3 font-semibold rounded-t-lg border-[1px] border-black bg-white"
      >
        All Rooms
      </div>
      <div
        class="room-types px-5 py-3 font-semibold rounded-t-lg border-[1px] border-black bg-white"
        v-for="(roomKey, index) in roomKeys"
        :key="index"
        @click="roomStore.selectRoomType(roomKey.roomType)"
      >
        {{ capitalizeAndSpace(roomKey.roomType) }}
      </div>
    </div>

    <div
      class="section-all-rooms relative -top-1 min-h-screen bg-white rounded-b-lg rounded-e-lg border-[1px] border-black gap-x-5 grid xl:grid-row-1 xl:grid-cols-1 lg:grid-cols-1 md:grid-cols-2 sm:grid-cols-1"
    >
      <div
        class="room bg-white rounded-lg p-5 m-5 shadow-md grid grid-cols-7 gap-x-5"
        v-for="(room, index) in roomStore.getMergeRooms"
        :key="room.roomId"
      >
        <img
          :src="room.imageUrl"
          :alt="`Room ${room.roomId}: ${room.name}`"
          class="w-full h-full rounded-lg object-cover col-span-2 "
        />
        <div class="room-reservation col-span-3 px-4">
          <h3 class="text-xl font-bold mb-2">
            {{ `Room ${room.roomId}: ${room.name}` }}
          </h3>
          <p class="text-gray-500 mb-4">
            {{ `${room.capacity.min}-${room.capacity.max} people` }}
          </p>
          <div class="grid grid-cols-4 gap-3 text-center">
            <div
              v-for="time in timeSlots"
              :key="time"
              class="bg-[#D7FEF2] p-2 rounded-lg"
            >
              {{ time }}
            </div>
            <button
              :key="index"
              v-for="time in timeSlots"
              class="bg-[#4992f2] text-white px-4 py-2 rounded-lg"
            >
              RESERVE
            </button>
          </div>
        </div>
        <div class="room-detail flex col-span-2">
          <div :key="n" class="h-full w-0.5 bg-slate-100"></div>
          <div class="ml-7">
            <h3 class="text-xl font-bold mb-2">Room Detail</h3>
            <p class="text-gray-500">
              <span class="font-semibold">Building:</span> {{ room.building }}
            </p>
            <p class="text-gray-500">
              <span class="font-semibold">Instruments:</span>
              {{ room.instruments.join(", ") }}
            </p>
            <p class="text-gray-500">
              <span class="font-semibold">Features:</span>
              {{ room.features.join(", ") }}
            </p>
            <p class="text-gray-500">
              <span class="font-semibold">Room Type:</span>
              {{ room.roomType }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
