<template>
  <v-simple-table dark>
    <template v-slot:default>
      <thead>
        <tr>
          <th class="text-left">
            세척
          </th>
          <th class="text-left">
            20:00
          </th>
          <th class="text-left">
            21:00
          </th>
          <th class="text-left">
            22:00
          </th>
          <th class="text-left">
            23:00
          </th>
          <th class="text-left">
            00:00
          </th>
          <th class="text-left">
            01:00
          </th>
          <th class="text-left">
            02:00
          </th>
          <th class="text-left">
            03:00
          </th>
          <th class="text-left">
            04:00
          </th>
          <th class="text-left">
            05:00
          </th>
          <th class="text-left">
            06:00
          </th>
          <th class="text-left">
            07:00
          </th>
          <th class="text-left">
            08:00
          </th>
          <th class="text-left">
            09:00
          </th>
          <th class="text-left">
            10:00
          </th>
          <th class="text-left">
            11:00
          </th>
          <th class="text-left">
            12:00
          </th>
          <th class="text-left">
            13:00
          </th>
          <th class="text-left">
            14:00
          </th>
          <th class="text-left">
            15:00
          </th>
          <th class="text-left">
            16:00
          </th>
          <th class="text-left">
            17:00
          </th>
          <th class="text-left">
            18:00
          </th>
          <th class="text-left">
            19:00
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in desserts" :key="item.name">
          <td>{{ item.name }}</td>
          <td>{{ item.calories }}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
export default {
  name: "OsSaOrderGridCard",
  data: () => ({
    desserts: [
      {
        name: "1호기",
        calories: 159
      },
      {
        name: "2호기",
        calories: 237
      },
      {
        name: "3호기",
        calories: 262
      },
      {
        name: "4호기",
        calories: 305
      },
      {
        name: "5호기",
        calories: 356
      },
      {
        name: "6호기",
        calories: 375
      },
      {
        name: "누적 생산",
        calories: 392
      },
      {
        name: "진척율",
        calories: 408
      }
    ]
  })
};
</script>

<style scoped></style>
