<template>
  <div
    class="itbkk-modal-new fixed inset-0 flex items-center justify-center z-50 modal-open"
    ref="modal"
  >
    <div class="absolute inset-0 bg-black opacity-50"></div>
    <div
      class="relative itbkk-board-modal max-w-2xl w-[600px] bg-white rounded-lg shadow-xl"
    >
      <h2 class="text-2xl font-bold p-4">New Board</h2>
      <hr />

      <!-- Name Input -->
      <label for="name" class="block text-gray-700 font-bold mb-2">Name</label>
      <input
        type="text"
        id="name"
        class="itbkk-board-name w-full border rounded-md px-3 py-2 h-10"
        placeholder="Enter board name"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from "vue";
import { useRoute } from "vue-router";

const modal = ref<HTMLElement | null>(null);

onMounted(() => {
  document.body.classList.add("overflow-hidden");
});

onBeforeUnmount(() => {
  document.body.classList.remove("overflow-hidden");
});


</script>
