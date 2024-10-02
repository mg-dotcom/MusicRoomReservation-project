<script setup lang="ts">
import { onUnmounted, onMounted, ref } from "vue";
import { useRoomStore } from "@/stores/RoomStore";
import { useRouter, useRoute } from "vue-router";
import { currentDate } from "@/libsUtils";
import Swal from "sweetalert2";

const router = useRouter();
const roomStore = useRoomStore();

const handleEscapeKeyPress = (e: KeyboardEvent) => {
  if (e.key === "Escape") {
    router.push({ name: "home" });
  }
};

const isShow = ref(false);

onMounted(() => {
  isShow.value = true;
  window.addEventListener("keydown", handleEscapeKeyPress);
});

if (useRoute().name === "reservation") {
  setTimeout(() => {
    isShow.value = false;
  }, 20);
}

onUnmounted(() => {
  window.removeEventListener("keydown", handleEscapeKeyPress);
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

      roomStore.reserveRoom({
        roomId: roomStore.getBookedRoom?.roomId ?? "",
        time: roomStore.getBookedRoom?.time ?? "",
        name: name.value,
        tel: tel.value,
      });
      router.push({ name: "home" });
    }
  });
};

const tel = ref(""); // Telephone input
const name = ref(""); // Name input

const validateTel = (e: any) => {
  if (e.inputType === "deleteContentBackward") {
    if (tel.value.endsWith("-")) {
      tel.value = tel.value.slice(0, -1);
    }
  }
  if (e.inputType === "insertText" && /^\d$/.test(e.data)) {
    if (tel.value.length === 3 || tel.value.length === 8) {
      tel.value += "-";
    }
    tel.value += e.data;
  }
  const digitsOnly = tel.value.replace(/\D/g, "").slice(0, 11);

  if (digitsOnly.length <= 3) {
    tel.value = digitsOnly;
  } else if (digitsOnly.length <= 7) {
    tel.value = `${digitsOnly.slice(0, 3)}-${digitsOnly.slice(3)}`;
  } else {
    tel.value = `${digitsOnly.slice(0, 3)}-${digitsOnly.slice(
      3,
      7
    )}-${digitsOnly.slice(7, 11)}`;
  }
};

const formatTel = (tel: string) => {
  const digitsOnly = tel.replace(/\D/g, "").slice(0, 11);

  if (digitsOnly.length <= 3) {
    return digitsOnly;
  } else if (digitsOnly.length <= 7) {
    return `${digitsOnly.slice(0, 3)}-${digitsOnly.slice(3)}`;
  } else {
    return `${digitsOnly.slice(0, 3)}-${digitsOnly.slice(
      3,
      7
    )}-${digitsOnly.slice(7, 11)}`;
  }
};

const handleKeyPress = (e: KeyboardEvent) => {
  const isValidKey = /^\d$/.test(e.key);
  if (!isValidKey) {
    e.preventDefault();
  }
};
</script>

<template>
  <div
    class="fixed inset-0 flex items-center justify-center z-50 modal-open p-4 sm:p-0"
  >
    <div class="absolute inset-0 bg-black opacity-50"></div>
    <div
      class="relative w-full max-w-4xl xl:scale-90 lg:scale-75 md:scale-75 bg-white rounded-lg shadow-xl reservation-modal overflow-y-auto"
      :class="
        isShow
          ? 'opacity-0 transition-opacity duration-500 ease-in-out'
          : 'opacity-100 transition-opacity duration-500 ease-in-out'
      "
    >
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
        <div class="grid sm:grid-cols-2 grid-cols-1 sm:gap-5">
          <!-- Changed to single column on small screens -->
          <div class="booked-room col-span-2">
            <h3 class="text-lg font-bold">Room Detail</h3>
          </div>
          <img
            :src="roomStore.getBookedRoom?.imageUrl"
            :alt="`Room ${roomStore.getBookedRoom?.roomId}: ${roomStore.getBookedRoom?.name}`"
            class="object-cover rounded-lg hidden"
            :class="{ 'sm:block': roomStore.getBookedRoom?.imageUrl }"
          />
          <div
            class="sm:ml-3 booked-room-detail flex flex-col justify-between mt-2"
          >
            <div>
              <div
                class="flex justify-between rounded-xl px-2 mb-2 gradient-bg"
              >
                <h4 class="text-lg font-bold">
                  {{ roomStore.getBookedRoom?.name }}
                </h4>
                <h4 class="text-lg font-bold">
                  {{ roomStore.getBookedRoom?.roomId }}
                </h4>
              </div>

              <p class="text-gray-500">
                <span class="font-semibold">Building:</span>
                {{ roomStore.getBookedRoom?.building }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Instruments:</span>
                {{ roomStore.getBookedRoom?.instruments.join(", ") }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Features:</span>
                {{ roomStore.getBookedRoom?.features.join(", ") }}
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Capacity:</span>
                {{ roomStore.getBookedRoom?.capacity.min }}-{{
                  roomStore.getBookedRoom?.capacity.max
                }}
                people
              </p>
              <p class="text-gray-500">
                <span class="font-semibold">Room Type:</span>
                {{ roomStore.getBookedRoom?.roomType }}
              </p>
            </div>

            <hr class="h-px my-3 bg-gray-200 border-0 dark:bg-gray-700" />

            <div>
              <h4 class="text-lg font-bold">Time</h4>
              <div class="flex justify-between">
                <p class="text-gray-500">
                  <span class="font-semibold">Time:</span>
                  {{ roomStore.getBookedRoom?.time }}
                </p>
                <p class="text-gray-500">
                  <span class="font-semibold">Date:</span>
                  {{ currentDate }}
                </p>
              </div>
            </div>
          </div>
          <div class="col-span-2">
            <label for="name" class="block font-medium text-gray-700">
              Name <span class="text-red-600">*</span>
            </label>
            <input
              maxlength="50"
              v-model.trim="name"
              placeholder="Enter your name"
              type="text"
              name="name"
              id="name"
              class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none"
              required
            />
          </div>
          <div class="mt-3 col-span-2">
            <label for="tel" class="block font-medium text-gray-700"
              >Telephone <span class="text-red-600">*</span></label
            >
            <input
              type="tel"
              name="tel"
              id="tel"
              @input="validateTel"
              @keypress="handleKeyPress"
              @blur="tel = formatTel(tel)"
              v-model.trim="tel"
              placeholder="090-0000-000"
              maxlength="12"
              class="mt-1 block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none border-gray-300"
            />
          </div>
        </div>
      </div>
      <div class="p-5 flex justify-end items-end border-t">
        <button
          class="bg-[#4992f2] text-white text-sm font-medium px-4 py-2 rounded-lg transition duration-300 transform hover:bg-[#3e7ac9]"
          :disabled="name.length === 0 || tel.length === 0"
          :class="
            name.length === 0 || tel.length === 0
              ? 'bg-gray-300 hover:bg-gray-400 cursor-not-allowed'
              : 'cursor-pointer'
          "
          @click="confirmReservation"
        >
          Reserve
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
