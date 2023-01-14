<script setup lang="ts">
import {ref} from "vue";

import axios from "axios";

const books = ref([]);

axios.get("/api/books-orderBy-latest?page=1").then((response) => {
  response.data.forEach((r: any) => {
    books.value.push(r);
  })
})

</script>

<template>
  <ul>
    <li v-for="book in books" :key="book.id">
      <div>
        작성자 : {{ book.member.username }}
      </div>

      <div class="mt-2">
        <router-link to="/contentStore"> 책제목 : {{ book.title }}</router-link>
      </div>

      <div class="mt-2">
        좋아요 : {{ book.likes }}
      </div>

      <div class="mt-2">
        북마크 : {{ book.bookMark }}
      </div>
    </li>
  </ul>

</template>

<style>

</style>