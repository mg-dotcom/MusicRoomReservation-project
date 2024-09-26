import { createRouter, createWebHistory } from "vue-router";
import HomePage from "@/views/HomePage.vue";
import Reservation from "../components/Reservation.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomePage,
      children: [
        {
          path: "reservation",
          name: "reservation",
          component: Reservation,
        },
      ],
    },
    {
      path: "/",
      redirect: { name: "home" },
    },
    {
      path: "/:pathMatch(.*)*",
      redirect: { name: "home" },
    },
  ],
});

export default router;
