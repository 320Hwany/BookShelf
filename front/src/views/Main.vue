<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter()

const saveBookPage = function () {
  router.push({name : "saveBook"})
}

const readBookPage = function () {
  router.push({name : "readBook"})
}

</script>

<template>

  <div>
    <h2>Book Shelf</h2>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="saveBookPage()">책 등록하기</el-button>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="readBookPage()">책 보러가기</el-button>
  </div>

</template>

<style>

</style>