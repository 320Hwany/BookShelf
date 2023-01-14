<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";
import {useRouter} from "vue-router";

const email = ref("")
const password = ref("")

const router = useRouter()

const login = function () {
  axios.post("/api/login", {
    email: email.value,
    password: password.value
  })
      .then(() => {
        router.replace({name: "main"});
      })
}
</script>

<template>

  <div>
    <h2>Book Shelf</h2>
  </div>

  <div class="mt-2">
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