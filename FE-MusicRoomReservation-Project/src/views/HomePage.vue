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
  }, 2300);
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
  isShow.value = true;
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

  setTimeout(() => {
    isShow.value = false;
  }, 500);
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
  isShow.value = true;
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
  setTimeout(() => {
    isShow.value = false;
  }, 500);
};

const clearAllFilter = () => {
  isShow.value = true;
  searchFilterArr.value = [];
  styleRoomTypes.value = "all";
  mergeRooms.value = roomStore.getMergeRooms;
  clearSelectAndGetAllRooms();
  setTimeout(() => {
    isShow.value = false;
  }, 500);
};

const clearSelectAndGetAllRooms = () => {
  roomOption.value = "Room";
  capacityOption.value = "Capacity";
  instrumentOption.value = "Instrument";
};

const handleSearch = () => {
  isShow.value = true;
  const input = searchInput.value.toLowerCase();

  const mergeRoomBySearch = roomStore.getMergeRooms.filter((room: any) => {
    const roomName = room.name.toLowerCase();
    return roomName.includes(input);
  });

  clearAllFilter();

  mergeRooms.value = mergeRoomBySearch; // Assuming mergeRooms is a ref
  setTimeout(() => {
    isShow.value = false;
  }, 500);
};

const clearSearch = () => {
  isShow.value = true;
  searchInput.value = "";
  mergeRooms.value = roomStore.getMergeRooms;
  setTimeout(() => {
    isShow.value = false;
  }, 500);
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

const showFilter = ref(false);
</script>

<template>
  <RouterView />
  <div
    class="filter-room-button lg:hidden flex justify-end p-2 flex-row items-center"
  >
    <p class="text-primary text-sm mr-1 font-medium">Filter</p>
    <button
      class="text-primary text-xl focus:outline-none"
      @click="showFilter = !showFilter"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="w-8 hover:text-primary-dark transition hover:bg-green-100 p-1 rounded-full"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M3 4h18M4 8h16M6 12h12M8 16h8"
        />
      </svg>
    </button>
  </div>

  <div class="w-full px-14 py-11">
    <h1 class="font-semibold text-xl lg:text-2xl text-center sm:text-left">
      Room Reservation
    </h1>
    <div
      :class="{
        'hidden opacity-0 transition-opacity duration-200': !showFilter,
        'block opacity-100 transition-opacity duration-200': showFilter,
      }"
    >
      <div class="section-filter">
        <div class="filter flex flex-col lg:flex-row my-4 justify-between">
          <div class="flex flex-col text-lg w-full lg:w-auto">
            <div
              class="select-filter flex flex-col lg:flex-row space-y-4 lg:space-x-3 lg:space-y-0 w-full lg:justify-center items-center text-lg lg:text-xl"
              ref="filterOption"
            >
              <div class="filter-room font-medium w-full lg:w-auto">
                <select
                  id="room"
                  class="p-1 w-full lg:w-auto"
                  v-model="roomOption"
                  @change="selectedFilter(roomOption as object)"
                  :class="{
            'border-primary': searchFilterArr.filter(
              (filter: any) => filter === roomOption
            ).length > 0,
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

              <div class="filter-capacity font-medium w-full lg:w-auto">
                <select
                  id="capacity"
                  class="p-1 w-full lg:w-auto"
                  v-model="capacityOption"
                  @change="selectedFilter(capacityOption as object)"
                  :class="{
            'border-primary': searchFilterArr.filter(
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

              <div class="filter-instrument font-medium w-full lg:w-auto">
                <select
                  id="instrument"
                  class="p-1 w-full lg:w-auto"
                  v-model="instrumentOption"
                  @change="selectedFilter(instrumentOption as object)"
                  :class="{
            'border-primary': searchFilterArr.filter(
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
            </div>
            <div class="flex mt-4 gap-3">
              <SearchButton @click="searchAllFilter" />
              <div
                class="my-2 text-sm text-slate-300 hover:text-slate-400 transition cursor-pointer w-fit"
                @click="clearAllFilter"
              >
                Clear All Filter
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="search-filter relative w-full lg:w-auto mt-4 lg:mt-0">
      <input
        type="text"
        v-model.trim="searchInput"
        @click="clearAllFilter"
        @input="handleSearch"
        placeholder="Search room name"
        class="search-input h-9 w-full lg:w-64 px-2 focus:ring-2 focus:ring-primary focus:outline-none rounded-l-md"
      />
      <button
        @click="clearSearch"
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
        @click="handleSearch"
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

    <div
      class="section-room-types flex flex-col sm:flex-row gap-x-3 gap-y-2 sm:gap-y-0 mt-11 text-xl relative z-0"
      ref="currentRoomType"
    >
      <div
        class="all-rooms lg:px-7 lg:py-3 font-semibold rounded-t-lg border-[1px] border-black bg-white relative z-5 w-full sm:w-auto text-lg p-2 lg:text-xl"
        :class="{
          'all-rooms-selected bg-primary pointer-events-none':
            styleRoomTypes === 'all',
        }"
        @click="selectedRoomType('all')"
      >
        <h2 class="relative z-10 text-center sm:text-left">All Rooms</h2>
        <div
          v-if="styleRoomTypes === 'all'"
          class="bg-white w-full h-[84%] absolute bottom-0 left-0"
        ></div>
      </div>

      <div
        class="room-types lg:px-5 lg:py-3 font-semibold rounded-t-lg border-[1px] border-black relative bg-white w-full sm:w-auto text-base p-2 lg:text-xl"
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
        <h2 class="relative z-10 text-center sm:text-left">
          {{ capitalizeAndSpace(room.roomType) }}
        </h2>
        <div
          v-if="styleRoomTypes === room.roomType"
          class="bg-white w-full h-[84%] absolute bottom-0 left-0"
        ></div>
      </div>
    </div>

    <div
      ref="roomList"
      class="section-all-rooms relative -top-1 bg-white rounded-b-lg rounded-e-lg border-[1px] border-black gap-x-5 grid grid-cols-1 sm:p-5"
    >
      <div v-if="mergeRooms.length === 0 && !isLoaded">
        <p class="text-center text-2xl font-semibold p-10 text-primary-dark">
          No room available
        </p>
      </div>
      <div v-if="isLoaded || (isShow && mergeRooms.length > 0)">
        <p class="text-center text-2xl font-semibold p-10 text-primary-dark">
          Loading...
        </p>
      </div>
      <RoomCard
        class="room-card"
        v-for="(room, index) in mergeRooms"
        v-if="!isLoaded"
        :class="isShow ? 'opacity-0 transition-opacity duration-200' : ''"
        :key="index"
        :time-slots="timeSlots"
      >
        <template #image>
          <img
            :src="room.imageUrl"
            :alt="`Room ${room.roomId}: ${room.name}`"
            class="h-56 w-full sm:w-96 rounded-lg object-cover col-span-2"
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
            class="bg-[#D7FEF2] px-2 py-1 sm:px-4 sm:py-2 rounded-lg transition duration-300 text-xs sm:text-base transform leading-tight"
          >
            {{ time }}
          </div>

          <button
            v-for="time in timeSlots"
            :disabled="roomStore.getRoomReservation[room.roomId]?.time === time"
            :key="time"
            :value="time"
            class="bg-[#4992f2] text-white p-2 sm:px-4 sm:py-2 rounded-lg flex items-center justify-center transition duration-300 transform hover:bg-[#3e7ac9] text-[0.5rem] font-semibold sm:text-base leading-tight"
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

.transition-opacity {
  transition: opacity 0.2s ease-in-out;
}
</style>
