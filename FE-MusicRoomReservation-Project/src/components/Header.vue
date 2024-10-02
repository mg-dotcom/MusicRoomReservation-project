<script setup>
import { currentDayDate } from "@/libsUtils";

// Function to format the date
</script>

<template>
  <div
    class="bg-primary relative flex flex-col justify-center sm:flex-col sm:h-auto md:justify-center lg:h-32 lg:flex-col lg:justify-center"
  >
    <div
      class="inset-0 flex flex-col py-4 items-center px-10 sm:py-6 md:justify-between md:flex-row lg:justify-between lg:absolute"
    >
      <img src="/logo.png" class="h-16 sm:h-16 lg:h-20" alt="Company logo" />
      <div
        class="bg-primary text-center p-2 text-shadow-solid shadow-primary-dark sm:mt-3 mt-3"
      >
        <h1 class="lg:text-3xl text-2xl text-white font-bold sm:text-2xl">
          Today's Hours : OPEN
        </h1>
        <p class="text-white font-medium text-lg lg:text-xl">
          {{ currentDayDate }}
        </p>
      </div>
    </div>
    <div class="flex flex-col sm:space-y-1 space-y-1">
      <div v-for="n in 7" :key="n" class="w-full h-1 bg-yellow-old"></div>
    </div>
  </div>
</template>

<style scoped></style>
