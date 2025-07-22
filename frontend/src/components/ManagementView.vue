<template>
  <div>
    <h2>Management</h2>
    <section>
      <h3>Deploy Process</h3>
      <input v-model="activityType" placeholder="Activity type" />
      <input type="file" @change="onFileChange" />
      <button @click="deployProcess">Deploy</button>
    </section>
  <section>
    <h3>Start Activity</h3>
    <input v-model="startType" placeholder="Activity type" />
    <button @click="startActivity">Start</button>
  </section>
  <section>
    <h3>Configure Wheel Prize</h3>
    <input v-model="wheelType" placeholder="Activity type" />
    <input v-model="prizeName" placeholder="Prize name" />
    <input v-model="prizeType" placeholder="Prize type" />
    <input v-model.number="rewardId" type="number" placeholder="Reward ID" />
    <input v-model.number="prizeProb" type="number" step="0.01" placeholder="Probability" />
    <button @click="savePrize">Save Prize</button>
  </section>
  <section>
    <h3>Configure Reward Item</h3>
    <input v-model="rewardType" placeholder="Reward type" />
    <input v-model="rewardName" placeholder="Name" />
    <input v-model="rewardDetail" placeholder="Detail" />
    <button @click="saveReward">Save Reward</button>
  </section>
  <p v-if="message">{{ message }}</p>
</div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const activityType = ref('')
const startType = ref('')
const fileContent = ref(null)
const message = ref('')
const wheelType = ref('')
const prizeName = ref('')
const prizeType = ref('')
const rewardId = ref(null)
const prizeProb = ref(0)

const rewardType = ref('')
const rewardName = ref('')
const rewardDetail = ref('')

function onFileChange(e) {
  const file = e.target.files[0]
  if (!file) {
    fileContent.value = null
    return
  }
  const reader = new FileReader()
  reader.onload = () => {
    fileContent.value = reader.result
  }
  reader.readAsText(file)
}

async function deployProcess() {
  if (!fileContent.value || !activityType.value) {
    message.value = 'Missing activity type or file'
    return
  }
  try {
    await axios.post(`/api/management/process?activityType=${activityType.value}`, fileContent.value, {
      headers: { 'Content-Type': 'text/plain' }
    })
    message.value = 'Deployed successfully'
  } catch (err) {
    message.value = 'Deployment failed'
  }
}

async function startActivity() {
  if (!startType.value) {
    message.value = 'Missing activity type'
    return
  }
  try {
    const res = await axios.post(`/api/management/start?activityType=${startType.value}`)
    message.value = 'Started process ' + res.data.processId
  } catch (err) {
    message.value = 'Start failed'
  }
}

async function savePrize() {
  if (!wheelType.value || !prizeName.value) {
    message.value = 'Missing fields'
    return
  }
  try {
    await axios.post(`/api/management/wheel/prize?activityType=${wheelType.value}&name=${encodeURIComponent(prizeName.value)}&prizeType=${prizeType.value}&probability=${prizeProb.value}${rewardId.value ? `&rewardId=${rewardId.value}` : ''}`)
    message.value = 'Prize saved'
  } catch (err) {
    message.value = 'Save failed'
  }
}

async function saveReward() {
  if (!rewardType.value || !rewardName.value) {
    message.value = 'Missing reward fields'
    return
  }
  try {
    await axios.post(`/api/management/reward?rewardType=${rewardType.value}&name=${encodeURIComponent(rewardName.value)}&detail=${encodeURIComponent(rewardDetail.value)}`)
    message.value = 'Reward saved'
  } catch (err) {
    message.value = 'Save reward failed'
  }
}
</script>


