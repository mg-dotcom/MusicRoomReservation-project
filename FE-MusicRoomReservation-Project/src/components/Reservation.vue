<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";
import { useRoomStore } from "@/stores/roomStore";
import { useRoute, useRouter } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();
const roomStore = useRoomStore();

const localTimeZone = ref(Intl.DateTimeFormat().resolvedOptions().timeZone);

const currentDate = new Date().toLocaleDateString("en-GB", {
  timeZone: localTimeZone.value,
  day: "numeric",
  month: "long",
  year: "numeric",
});

const confirmReservation = () => {
  Swal.fire({
    title: "Reservation Confirmation",
    text: "Are you sure you want to reserve this room?",
    icon: "question",
    showCancelButton: true,
    confirmButtonText: "Yes",
    cancelButtonText: "No",
    confirmButtonColor: "#4992f2",
    cancelButtonColor: "#f56565",
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "Reserved!",
        text: "Your reservation has been confirmed.",
        icon: "success",
        confirmButtonColor: "#4992f2",
      });
      router.push({ name: "home" });
    }
  });
};
</script>

<template>
  <div
    class="reservation-modal fixed inset-0 flex items-center justify-center z-50 modal-open"
  >
    <div class="absolute inset-0 bg-black opacity-50"></div>
    <div class="relative w-[900px] bg-white rounded-lg shadow-xl">
      <div
        class="flex justify-between items-center p-5 border-b border-gray-200"
      >
        <h3 class="text-xl font-bold">Reservation</h3>
        <button
          class="text-gray-500 hover:bg-gray-200 rounded-full p-1"
          @click="router.push({ name: 'home' })"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-6 w-6"
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
      </div>
      <div class="p-5">
        <div class="grid grid-cols-2 gap-5">
          <div class="booked-room col-span-2">
            <h3 class="text-lg font-bold">Room Detail</h3>
          </div>
          <img
            :src="roomStore.getBookedRoom.imageUrl"
            :alt="`Room ${roomStore.getBookedRoom.roomId}: ${roomStore.getBookedRoom.name}`"
            class="object-cover rounded-lg"
          />
          <div class="ml-3 booked-room-detail flex flex-col justify-between">
            <div>
              <div class="flex justify-between">
                <h4 class="text-lg font-bold">
                  {{ roomStore.getBookedRoom.name }}
                </h4>
                <h4 class="text-lg font-bold">
                  {{ roomStore.getBookedRoom.roomId }}
                </h4>
              </div>

              <p class="text-gray-500">
                <span class="font-semibold">Building:</span>
                {{ roomStore.getBookedRoom.building }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Instruments:</span>
                {{ roomStore.getBookedRoom.instruments.join(", ") }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Features:</span>
                {{ roomStore.getBookedRoom.features.join(", ") }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Capacity:</span>
                {{ roomStore.getBookedRoom.capacity.min }}-{{
                  roomStore.getBookedRoom.capacity.max
                }}
                people
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Room Type:</span>
                {{ roomStore.getBookedRoom.roomType }}
              </p>
            </div>

            <hr class="h-px bg-gray-200 border-0 dark:bg-gray-700" />

            <div class="">
              <h4 class="text-lg font-bold">Time</h4>
              <div class="flex justify-between">
                <p class="text-gray-500">
                  <span class="font-semibold">Time:</span>
                  {{ roomStore.getBookedRoom.time }}
                </p>
                <p class="text-gray-500">
                  <span class="font-semibold">Date:</span>
                  {{ currentDate }}
                </p>
              </div>
            </div>
          </div>
          <div>
            <label for="name" class="block text-sm font-medium text-gray-700">
              Name
            </label>
            <input
              type="text"
              name="name"
              id="name"
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:border-primary-dark sm:text-sm"
            />
          </div>
          <div>
            <label for="tel" class="block text-sm font-medium text-gray-700">
              Tel.
            </label>
            <input type="tel" name="tel" id="tel" class="mt-1 block w-full px-3
            py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none
            focus:border-primary-dark sm:text-sm"
          </div>
        </div>
      </div>
      <div class="p-5 flex justify-end items-end border-t">
        <button
          class="bg-[#4992f2] text-white text-sm font-medium px-4 py-2 rounded-lg transition duration-300 transform hover:bg-[#3e7ac9]"
          @click="confirmReservation"
        >
          Reserve
        </button>
      </div>
    </div>
  </div>
</template>
