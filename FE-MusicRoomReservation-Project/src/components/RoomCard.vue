<script setup lang="ts">
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps<{
  timeSlots: string[];
}>();
</script>

<template>
  <div
    class="room bg-white rounded-lg p-5 m-5 shadow-lg transition-transform transform hover:scale-[1.02] duration-300"
  >
    <div class="grid grid-cols-7 gap-x-5">
      <!-- Image Slot -->
      <slot name="image">image ...</slot>

      <!-- Room Reservation Slot -->
      <div class="room-reservation col-span-3 px-4">
        <h3 class="text-xl font-bold mb-2 text-gray-800">
          <slot name="room-name">room name ...</slot>
        </h3>
        <p class="text-gray-500 mb-4">
          <slot name="capacity">capacity ...</slot>
        </p>

        <div class="grid grid-cols-4 gap-3 text-center mt-3">
          <div
            v-for="time in props.timeSlots"
            :key="time"
            class="bg-[#D7FEF2] p-2 rounded-lg transition duration-300 transform"
          >
            {{ time }}
          </div>
          <button
            v-for="time in props.timeSlots"
            :key="time"
            class="bg-[#4992f2] text-white px-4 py-2 rounded-lg transition duration-300 transform hover:bg-[#3e7ac9]"
            @click="router.push({ name: 'reservation' })"
          >
            RESERVE
          </button>
        </div>
      </div>

      <!-- Divider -->
      <div class="room-detail flex col-span-2">
        <div class="h-full w-0.5 bg-slate-200"></div>
        <div class="ml-7">
          <h3 class="text-xl font-bold mb-2 text-gray-800">Room Detail</h3>
          <slot name="details"> </slot>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped></style>
