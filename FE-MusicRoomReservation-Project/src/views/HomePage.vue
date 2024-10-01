<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from "vue";
import { useRoomStore } from "@/stores/RoomStore";
import { RouterView, useRoute } from "vue-router";
import RoomCard from "@/components/RoomCard.vue";
import SearchButton from "@/components/SearchButton.vue";
import { capitalizeAndSpace } from "@/libsUtils";
import router from "@/router";
import { set } from "@vueuse/core";

const roomStore = useRoomStore();
const roomTypes = ref<{ roomType: string; rooms: any }[]>([]);
const styleRoomTypes = ref<string>();

const roomOption = ref<object | string>("Room");
const capacityOption = ref<object | string>("Capacity");
const instrumentOption = ref<object | string>("Instrument");
const searchFilterArr = ref<any[]>([]);
const searchInput = ref<string>("");

const timeSlots = ref<string[]>([
  "08:30 - 10:20",
  "10:30 - 12:20",
  "12:30 - 14:20",
  "14:30 - 16:20",
]);

// const route = useRoute();
const isLoaded = ref(false);
const isShow = ref(false);
onMounted(async () => {
  isLoaded.value = true;
  await roomStore.loadRooms();

  roomTypes.value = Object.keys(roomStore.getRooms).map((key) => {
    return {
      roomType: key,
      rooms: (roomStore.getRooms as Record<string, any>)[key],
    };
  });
  mergeRooms.value = roomStore.getMergeRooms;
  styleRoomTypes.value = "all";

  setTimeout(() => {
    isLoaded.value = false;
  }, 3000);
});

const distinctCapacity = computed(() => {
  const uniqueCapacities = new Set();
  return roomStore.getMergeRooms.filter((room: any) => {
    const capacityStr = `${room.capacity.min}-${room.capacity.max}`;
    return uniqueCapacities.has(capacityStr)
      ? false
      : uniqueCapacities.add(capacityStr);
  });
});

const selectedRoomType = (roomType: string) => {
  if (roomType === "all") {
    mergeRooms.value = roomStore.getMergeRooms;
    styleRoomTypes.value = "all";
  } else {
    const rooms = roomStore.getRooms as Record<string, any>;

    mergeRooms.value = rooms[roomType] || [];
    styleRoomTypes.value = roomType;

    searchFilterArr.value = [];
    clearSelectAndGetAllRooms();
  }
};

const selectedFilter = (option: object) => {
  if (searchFilterArr.value.length === 0) {
    searchFilterArr.value.push(option);
  } else {
    const filterIndex = searchFilterArr.value.findIndex((filter) => {
      return Object.keys(filter)[0] === Object.keys(option)[0];
    });

    if (filterIndex === -1) {
      searchFilterArr.value.push(option);
    } else {
      searchFilterArr.value[filterIndex] = option;
    }
  }
};

const searchAllFilter = () => {
  let filteredRooms = roomStore.getMergeRooms;

  const roomTypeFilter = searchFilterArr.value.find((filter) => {
    return filter.roomType;
  });

  // if have roomType filter then filter by roomType first
  if (roomTypeFilter) {
    filteredRooms =
      (roomStore.getRooms as Record<string, any>)[roomTypeFilter.roomType] ||
      [];
  }

  searchFilterArr.value.forEach((filter) => {
    const filterKey = Object.keys(filter)[0];

    if (filterKey === "capacity") {
      filteredRooms = filteredRooms.filter((room: any) => {
        return (
          room.capacity.min >= filter.capacity.min &&
          room.capacity.max <= filter.capacity.max
        );
      });
    } else if (filterKey === "instruments") {
      filteredRooms = filteredRooms.filter((room) => {
        return (filter.instruments as string[]).every((instrument: string) =>
          room.instruments.includes(instrument)
        );
      });
    }
  });
  styleRoomTypes.value = "all";
  mergeRooms.value = filteredRooms;
};

const clearAllFilter = () => {
  searchFilterArr.value = [];
  styleRoomTypes.value = "all";
  mergeRooms.value = roomStore.getMergeRooms;
  clearSelectAndGetAllRooms();
};

const clearSelectAndGetAllRooms = () => {
  roomOption.value = "Room";
  capacityOption.value = "Capacity";
  instrumentOption.value = "Instrument";
};

const handleSearch = () => {
  const input = searchInput.value.toLowerCase();

  const mergeRoomBySearch = roomStore.getMergeRooms.filter((room: any) => {
    const roomName = room.name.toLowerCase();
    return roomName.includes(input);
  });

  clearAllFilter();

  mergeRooms.value = mergeRoomBySearch; // Assuming mergeRooms is a ref
};

const mergeRooms = ref<any[]>([]);

const filterOption = ref<HTMLElement | null>(null);
const roomList = ref<HTMLElement | null>(null);
const currentRoomType = ref<HTMLElement | null>(null);

const reserveRoom = (room: object, time: string) => {
  const selectedRoom = {
    ...room,
    ...{ time: time },
  } as BookedRoom;
  roomStore.setSelectedRoom(selectedRoom);
  router.push({ name: "reservation" });
};
</script>

<template>
  <RouterView />
  <div class="w-full px-14 py-11">
    <div class="section-filter">
      <h1 class="text-3xl font-semibold">Room Reservation</h1>
      <div class="filter flex my-4 justify-between">
        <div class="flex flex-col text-lg">
          <div
            class="select-filter flex space-x-3 w-full justify-center items-center"
            ref="filterOption"
          >
            <div class="filter-room font-medium">
              <select
                id="room"
                class="p-1"
                v-model="roomOption"
                @change="selectedFilter(roomOption as object)"
                :class="{
                  'border-primary':
                    searchFilterArr.filter((filter: any) => filter === roomOption)
                      .length > 0,
                }"
              >
                <option value="Room" disabled selected hidden>Room</option>
                <option
                  v-for="(room, index) in roomTypes"
                  :value="{ roomType: room.roomType }"
                  :key="index"
                >
                  {{ capitalizeAndSpace(room.roomType) }}
                </option>
              </select>
            </div>

            <div class="filter-capacity font-medium">
              <select
                id="capacity"
                class="p-1"
                v-model="capacityOption"
                @change="selectedFilter(capacityOption as object)"
                :class="{
                  'border-primary':
                    searchFilterArr.filter(
                      (filter: any) => filter === capacityOption
                    ).length > 0,
                }"
              >
                <option value="Capacity" disabled selected hidden>
                  Capacity
                </option>

                <option
                  v-for="(room, index) in distinctCapacity"
                  :value="{ capacity: room.capacity }"
                  :key="index"
                >
                  {{ `${room.capacity.min}-${room.capacity.max} people` }}
                </option>
              </select>
            </div>
            <div class="filter-instrument font-medium">
              <select
                id="instrument"
                class="p-1"
                v-model="instrumentOption"
                @change="selectedFilter(instrumentOption as object)"
                :class="{
                  'border-primary':
                    searchFilterArr.filter(
                      (filter: any) => filter === instrumentOption
                    ).length > 0,
                }"
              >
                <option value="Instrument" disabled selected hidden>
                  Instrument
                </option>
                <option
                  v-for="(room, index) in roomStore.getMergeRooms"
                  :value="{ instruments: room.instruments }"
                  :key="index"
                >
                  {{ room.instruments.join(", ") }}
                </option>
              </select>
            </div>
            <SearchButton @click="searchAllFilter" />
          </div>
          <div
            class="my-2 text-sm text-slate-300 hover:text-slate-400 transition cursor-pointer w-fit"
            @click="clearAllFilter"
          >
            Clear All Filter
          </div>
        </div>
        <div class="search-filter relative">
          <input
            type="text"
            v-model.trim="searchInput"
            @click="clearAllFilter"
            @input="handleSearch"
            placeholder="Search room name"
            class="search-input h-9 w-64 px-2 focus:ring-2 focus:ring-primary focus:outline-none rounded-l-md"
          />
          <button
            @click="searchInput = ''"
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

    <div
      class="section-room-types flex gap-x-3 mt-11 text-xl relative z-0"
      ref="currentRoomType"
    >
      <div
        class="all-rooms px-7 py-3 font-semibold rounded-t-lg border-[1px] border-black bg-white relative z-5"
        :class="{
          'all-rooms-selected bg-primary pointer-events-none':
            styleRoomTypes === 'all',
        }"
        @click="selectedRoomType('all')"
      >
        <h2 class="relative z-10">All Rooms</h2>
        <div
          v-if="styleRoomTypes === 'all'"
          class="bg-white w-full h-[84%] absolute bottom-0 left-0"
        ></div>
      </div>

      <div
        class="room-types px-5 py-3 font-semibold rounded-t-lg border-[1px] border-black relative bg-white"
        :class="{
          'room-types-selected bg-primary pointer-events-none':
            styleRoomTypes === room.roomType,
        }"
        v-for="(room, index) in roomTypes"
        :key="index"
        @click="
          () => {
            selectedRoomType(room.roomType);
          }
        "
      >
        <h2 class="relative z-10">{{ capitalizeAndSpace(room.roomType) }}</h2>

        <div
          v-if="styleRoomTypes === room.roomType"
          class="bg-white w-full h-[84%] absolute bottom-0 left-0"
        ></div>
      </div>
    </div>

    <div
      ref="roomList"
      class="section-all-rooms relative -top-1 bg-white rounded-b-lg rounded-e-lg border-[1px] border-black gap-x-5 grid xl:grid-row-1 xl:grid-cols-1 lg:grid-cols-1 md:grid-cols-2 sm:grid-cols-1"
    >
      <div v-if="mergeRooms.length === 0">
        <p class="text-center text-2xl font-semibold p-10 text-primary-dark">
          No room available
        </p>
      </div>
      <div v-if="isLoaded">
        <p class="text-center text-2xl font-semibold p-10 text-primary-dark">
          Loading...
        </p>
      </div>
      <RoomCard
        class="room-card"
        v-for="(room, index) in mergeRooms"
        v-if="!isLoaded"
        :key="index"
        :time-slots="timeSlots"
      >
        <template #image>
          <img
            :src="room.imageUrl"
            :alt="`Room ${room.roomId}: ${room.name}`"
            class="h-56 w-96 rounded-lg object-cover col-span-2"
          />
        </template>

        <template #room-name>
          {{ `Room ${room.roomId}: ${room.name}` }}
        </template>

        <template #capacity>
          {{ `${room.capacity.min}-${room.capacity.max} people` }}
        </template>

        <template #timeSlots>
          <div
            v-for="time in timeSlots"
            :key="time"
            class="bg-[#D7FEF2] p-2 rounded-lg transition duration-300 transform"
          >
            {{ time }}
          </div>

          <button
            v-for="time in timeSlots"
            :disabled="roomStore.getRoomReservation[room.roomId]?.time === time"
            :key="time"
            :value="time"
            class="bg-[#4992f2] text-white px-4 py-2 rounded-lg transition duration-300 transform hover:bg-[#3e7ac9]"
            :class="{
              'bg-[#4992f2]':
                roomStore.getRoomReservation[room.roomId]?.time !== time,
              'bg-gray-300 hover:bg-gray-400 cursor-not-allowed':
                roomStore.getRoomReservation[room.roomId]?.time === time,
            }"
            @click="reserveRoom(room, time)"
          >
            RESERVE
          </button>
        </template>
        <template #details>
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
            <span class="font-semibold">Room Type:</span> {{ room.roomType }}
          </p>
        </template>
      </RoomCard>
    </div>
  </div>
</template>

<style scoped>
.bg-primary {
  background-color: #71dbbb;
}
</style>
