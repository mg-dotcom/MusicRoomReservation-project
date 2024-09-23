<script setup></script>

<template>
  <div class="w-full px-11 py-7">
    <h1 class="text-2xl font-semibold">Room Reservation</h1>
    <div class="filter flex my-2 justify-between">
      <div class="flex flex-col">
        <div class="select-filter flex space-x-3 w-full">
          <div class="filter-room font-medium">
            <select id="room" class="p-1">
              <option value="" disabled selected hidden>Room</option>
              <option value="room1">Room 1</option>
              <option value="room2">Room 2</option>
              <option value="room3">Room 3</option>
            </select>
          </div>
          <div class="filter-count font-medium">
            <select id="count" class="p-1">
              <option value="" disabled selected hidden>Count</option>
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
            </select>
          </div>
          <div class="filter-instrument font-medium">
            <select id="instrument" class="p-1">
              <option value="" disabled selected hidden>
                Music instrument
              </option>
              <option value="guitar">Guitar</option>
              <option value="piano">Piano</option>
              <option value="drums">Drums</option>
            </select>
          </div>
          <button class="btn-search-filter h-8 px-2 font-medium">Search</button>
        </div>
        <div
          class="my-2 text-sm text-slate-300 hover:text-slate-400 transition cursor-pointer"
        >
          Clear All Filter
        </div>
      </div>
      <div class="search-filter relative">
        <input
          type="text"
          placeholder="Search"
          class="search-input h-8 w-64 px-2 focus:ring-2 focus:ring-primary focus:outline-none rounded-l-md"
        />
        <button
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
          class="search-filter-submit absolute top-0 right-0 h-8 px-2 rounded-r-md rounded-l-none bg-primary text-white hover:bg-primary-dark"
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
</template>

<style scoped></style>
