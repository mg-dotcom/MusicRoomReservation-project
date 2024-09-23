<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import { useCounterStore } from "./stores/counter";
import Header from "./components/Header.vue";
</script>

<template>
  <Header />
  <RouterView />
</template>

<style scoped></style>
