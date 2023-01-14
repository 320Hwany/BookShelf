<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import router from "@/router";

const contentStores = ref([]);
const page = ref();
const content = ref();

axios.get("/api/book-content/{bookId}").then((response) => {
  response.data.forEach((r: any) => {
    contentStores.value.push(r);
  })
})

const saveContentPage = function () {
  router.push({name : "saveContent"})
}

</script>

<template>
  <ul>
    <li v-for="contentStore in contentStores" :key="contentStore.id">
      <div>
        {{ contentStore.page }}
      </div>

      <div class="mt-2">
        {{ contentStore.content }}
      </div>
    </li>
  </ul>

  <div class="mt-2">
    <el-button type="primary" @click="saveContentPage()">내용 추가하기</el-button>
  </div>

</template>

<style>

</style>