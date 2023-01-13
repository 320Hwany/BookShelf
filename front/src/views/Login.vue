<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";

const email = ref("")
const password = ref("")

const login = function () {
  axios.post("http://localhost:8080/login", {
    email: email.value,
    password: password.value
  })
}
</script>

<template>
  <div>
<el-input v-model="email" placeholder="이메일을 입력해주세요" />
  </div>

  <div class="mt-2">
<el-input v-model="password" type="password" placeholder="비밀번호를 입력해주세요" />
  </div>

  <div class="mt-2">
  <el-button type="primary" @click="login()">로그인 하기</el-button>
  </div>

</template>

<style>

</style>