<template>
  <div>
    <h2>Client Participation</h2>
    <input v-model="processId" placeholder="Process ID" />
    <button @click="loadTasks">Load Tasks</button>
    <ul>
      <li v-for="task in tasks" :key="task.id">
        {{ task.name }} <button @click="complete(task.id)">Complete</button>
      </li>
    </ul>
    <section>
      <h3>Lottery Wheel</h3>
      <input v-model="wheelType" placeholder="Activity type" />
      <input v-model="userId" placeholder="User ID" />
      <button @click="loadWheel">Load Wheel</button>
      <ul>
        <li v-for="p in prizes" :key="p.id">{{ p.prizeName }} ({{ p.probability }})</li>
      </ul>
      <button @click="draw">Draw</button>
      <p v-if="drawResult">Prize: {{ drawResult }}</p>
      <h4>Records</h4>
      <ul>
        <li v-for="r in records" :key="r.id">{{ r.prizeName || 'none' }} - {{ r.drawnAt }}</li>
      </ul>
    </section>
    <section>
      <h3>Claim Reward</h3>
      <input v-model.number="rewardId" type="number" placeholder="Reward ID" />
      <input v-model="userId" placeholder="User ID" />
      <button @click="issueReward">Issue</button>
      <ul>
        <li v-for="i in issued" :key="i.id">Reward {{ i.rewardId }} at {{ i.issuedAt }}</li>
      </ul>
    </section>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const processId = ref('')
const tasks = ref([])
const message = ref('')
const wheelType = ref('')
const userId = ref('')
const prizes = ref([])
const drawResult = ref('')
const records = ref([])
const rewardId = ref(null)
const issued = ref([])

async function loadTasks() {
  try {
    const res = await axios.get(`/api/client/tasks?processId=${processId.value}`)
    tasks.value = res.data
    message.value = ''
  } catch (err) {
    message.value = 'Failed to load tasks'
  }
}

async function complete(id) {
  try {
    await axios.post(`/api/client/complete?taskId=${id}`)
    message.value = 'Task completed'
    await loadTasks()
  } catch (err) {
    message.value = 'Failed to complete task'
  }
}

async function loadWheel() {
  try {
    const res = await axios.get(`/api/client/wheel?activityType=${wheelType.value}`)
    prizes.value = res.data
  } catch (err) {
    message.value = 'Failed to load wheel'
  }
}

async function draw() {
  try {
    const res = await axios.post(`/api/client/draw?activityType=${wheelType.value}&userId=${userId.value}`)
    drawResult.value = res.data.prize || 'no prize'
    const recRes = await axios.get(`/api/client/draw-records?activityType=${wheelType.value}&userId=${userId.value}`)
    records.value = recRes.data
  } catch (err) {
    message.value = 'Draw failed'
  }
}

async function issueReward() {
  if (!rewardId.value || !userId.value) {
    message.value = 'Missing reward or user'
    return
  }
  try {
    const res = await axios.post(`/api/client/reward/issue?rewardId=${rewardId.value}&userId=${userId.value}`)
    issued.value = [...issued.value, res.data]
    const all = await axios.get(`/api/client/reward/records?userId=${userId.value}`)
    issued.value = all.data
    message.value = ''
  } catch (err) {
    message.value = 'Issue failed'
  }
}
</script>


