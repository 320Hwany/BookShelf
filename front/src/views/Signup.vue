<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";

const username = ref("")
const email = ref("")
const password = ref("")
const age = ref("")

const router = useRouter()

const signup = function () {
  axios.post("/api/signup", {
    username: username.value,
    email: email.value,
    password: password.value,
    age: age.value
  })
      .then(() => {
        router.replace({name: "home"});
      })
}
</script>

<template>

  <div>
    <h2>Book Shelf</h2>
  </div>

  <div class="mt-2">
    <el-input v-model="username" placeholder="닉네임을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="email" placeholder="이메일을 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="password" type="password" placeholder="비밀번호를 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-input v-model="age" type="age" placeholder="나이를 입력해주세요" />
  </div>

  <div class="mt-2">
    <el-button type="primary" @click="signup()">회원가입 하기</el-button>
  </div>

</template>

<style>

</style>