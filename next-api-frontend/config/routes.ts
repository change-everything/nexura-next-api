export default [
  { path: '/', name: '首页', icon: 'smile', component: './Index' },
  {
    path: '/interface/:id',
    name: '查看接口',
    icon: 'smile',
    component: './InterfaceInfo',
    hideInMenu: true,
  },
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      {
        name: '接口管理',
        icon: 'table',
        path: '/admin/interface',
        component: './Admin/InterfaceInfo',
      },
      {
        name: '接口监控',
        icon: 'table',
        path: '/admin/analysis',
        component: './Admin/InterfaceAnalysis',
      },
    ],
  },
  { name: '个人中心', icon: 'user', path: '/user/center', component: './User/Center' },

  { path: '*', layout: false, component: './404' },
];
