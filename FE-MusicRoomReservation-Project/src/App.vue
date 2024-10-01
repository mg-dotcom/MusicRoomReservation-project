<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import { ref } from "vue";
import Header from "./components/Header.vue";
</script>

<template>
  <Header />

  <RouterView />
</template>

<style scoped></style>
