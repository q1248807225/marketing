import { createRouter, createWebHistory } from 'vue-router'
import ManagementView from './components/ManagementView.vue'
import ClientView from './components/ClientView.vue'

const routes = [
  { path: '/', redirect: '/management' },
  { path: '/management', component: ManagementView },
  { path: '/client', component: ClientView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

