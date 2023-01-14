<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";

const title = ref("")

const router = useRouter()

const saveBook = function () {
  axios.post("/api/book", {
    title: title.value
  })
      .then(() => {
        router.replace({name: "main"});
      })
}
</script>

<template>

  <div>
    <h2>책 등록 페이지</h2>
  </div>

  <div class="mt-2">
    <el-input v-model="title" placeholder="책 제목을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="saveBook()">책 등록하기</el-button>
  </div>

</template>

<style>

</style>