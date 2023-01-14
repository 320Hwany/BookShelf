<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import router from "@/router";

const contentStores = ref([]);
const page = ref();
const content = ref();

const props = defineProps({
  bookId: {
    type: [Number, String],
    require: true,
  },
});

const saveContent = function () {
  axios.post(`/book-content/${props.bookId}`, {
    page: page.value,
    content: content.value
  })
      .then(() => {
        router.replace({name: "main"});
      })
}

</script>

<template>

  <div class="mt-2">
    <el-input v-model="page" placeholder="페이지를 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="content" placeholder="내용을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="saveContent()">내용 추가하기</el-button>
  </div>

</template>

<style>

</style>