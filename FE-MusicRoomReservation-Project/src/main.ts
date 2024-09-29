import "./assets/main.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import App from "./App.vue";
import router from "./router";
import VueSweetAlert from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate); // Pinia persisted state plugin added here

const app = createApp(App);
app.use(pinia); // Pinia used after the plugin is applied
app.use(router);
app.use(VueSweetAlert);

app.mount("#app");
