import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import UserMana from '@/components/UserMana'
import TaskList from '@/components/TaskList'
import PostTask from '@/components/PostTask'
import TaskDetail from '@/components/TaskDetail'
import TaskCfg from '@/components/TaskCfg'

Vue.use(Router)

// ルーティング設定
export default new Router({
  routes: [
    {
      path: '/',
      name: 'ログイン',
      hidden: true,
      component: Login
    },
    {
      path: '/home',
      name: '',
      component: Home,
      hidden: true
    },
    {
      path: '/home',
      component: Home,
      name: 'タスク管理',
      iconCls: 'fa fa-tasks', // アイコンは必要に応じて変更
      children: [
        {
          path: '/taskList',
          name: 'タスクリスト',
          component: TaskList,
          meta: { keepAlive: true }
        },
        {
          path: '/postTask',
          name: 'タスク作成',
          component: PostTask,
          hidden: true,
          meta: { keepAlive: false }
        },
        {
          path: '/taskDetail',
          name: 'タスク詳細',
          component: TaskDetail,
          hidden: true,
          meta: { keepAlive: false }
        },
        {
          path: '/editTask',
          name: 'タスク編集',
          component: PostTask,
          hidden: true,
          meta: { keepAlive: false }
        },
        {
          path: '/taskCfg',
          name: '通知設定',
          component: TaskCfg,
          hidden: true,
          meta: { keepAlive: false }
        }
      ]
    },
    {
      path: '/home',
      component: Home,
      name: 'ユーザー管理',
      children: [
        {
          path: '/user',
          iconCls: 'fa fa-user-o',
          name: 'ユーザー管理',
          component: UserMana
        }
      ]
    }
  ]
})