<script setup>
import { currentDayDate } from "@/libsUtils";
</script>

<template>
  <div class="h-28 bg-primary relative flex flex-col justify-center">
    <div class="absolute inset-0 flex justify-between items-center px-10">
      <img src="/logo.png" class="h-20" alt="Company logo" />
      <div
        class="bg-primary text-center p-2 text-shadow-solid shadow-primary-dark"
      >
        <h1 class="text-3xl text-white font-bold">Today's Hours : OPEN</h1>
        <p class="text-white text-xl font-medium">
          {{ currentDayDate }}
        </p>
      </div>
    </div>
    <div class="flex flex-col space-y-3">
      <div v-for="n in 5" :key="n" class="w-full h-1 bg-yellow-old"></div>
    </div>
  </div>
</template>

<style scoped></style>
