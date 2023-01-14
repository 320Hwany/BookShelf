import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/Login.vue";
import SignupView from "../views/Signup.vue";
import MainView from "../views/Main.vue";
import SaveBookView from "../views/SaveBook.vue";
import ReadBookView from "../views/ReadBook.vue";
import ContentStoreView from "@/views/ContentStore.vue";
import SaveContentView from "@/views/SaveContent.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/login",
      name: "login",
      component: LoginView
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupView
    },
    {
      path: "/main",
      name: "main",
      component: MainView
    },
    {
      path: "/saveBook",
      name: "saveBook",
      component: SaveBookView
    },
    {
      path: "/readBook",
      name: "readBook",
      component: ReadBookView
    },
    {
      path: "/contentStore",
      name: "contentStore",
      component: ContentStoreView
    },
    {
      path: "/saveContent",
      name: "saveContent",
      component: SaveContentView
    },
    // {
    //   path: "/book-content/{bookId}",
    //   name: "contentStore",
    //   component: ContentStoreView
    // }
    // {
    //   path: "/about",
    //   name: "about",
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import("../views/AboutView.vue"),
    // },
  ],
});

export default router;
