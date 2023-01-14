<script setup lang="ts">

import {useRouter} from "vue-router";

const router = useRouter()
const getLoginPage = function () {
  router.push({name : "login"})
}

const getSignupPage = function () {
  router.push({name : "signup"})
}

const LoginPass = function () {
  router.push({name : "main"})
}

</script>

<template>

  <div>
    <h2>Book Shelf</h2>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="getLoginPage()">로그인 하기</el-button>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="getSignupPage()">회원가입 하기 </el-button>
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="LoginPass()">로그인 없이 이용하기</el-button>
  </div>

</template>

<style>

</style>
