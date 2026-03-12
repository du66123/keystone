<script setup lang="ts">
import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteUserApi, getUserListApi } from '#/api/sys/user';
import { Page } from '@vben/common-ui';
import { message, Modal } from 'ant-design-vue';

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    collapsed: false,
    schema: [
      {
        component: 'Input',
        fieldName: 'username',
        label: '用户名',
      },
      {
        component: 'Input',
        fieldName: 'nickName',
        label: '用户昵称',
      },
      {
        component: 'Input',
        fieldName: 'phonenumber',
        label: '手机号码',
      },
    ],
    showCollapseButton: false,
    submitButtonOptions: { content: '查询' },
  },
  gridOptions: {
    columns: [
      { type: 'seq', width: 50 },
      { field: 'userId', title: '用户ID', width: 80 },
      { field: 'username', title: '用户名' },
      { field: 'nickName', title: '用户昵称' },
      { field: 'phonenumber', title: '手机号码' },
      { field: 'status', title: '状态', width: 80 },
      { field: 'createTime', title: '创建时间' },
      { title: '操作', field: 'action', width: 150, fixed: 'right' }
    ],
    proxyConfig: {
      ajax: {
        query: async ({ params }) => {
          // If backend returns a raw list inside Result.data
          const data = await getUserListApi(params);
          return { items: data || [] };
        },
      },
    },
    toolbarConfig: {
      custom: true,
      export: true,
      zoom: true,
      refresh: true,
    },
  },
});

function handleAdd() {
  message.info('新增用户弹窗待实现');
}

function handleEdit(row: any) {
  message.info(`编辑用户: ${row.username}`);
}

function handleDelete(row: any) {
  Modal.confirm({
    title: '确认删除',
    content: `是否确认删除用户 "${row.username}"？`,
    onOk: async () => {
      await deleteUserApi([row.userId]);
      message.success('删除成功');
      gridApi.reload();
    },
  });
}
</script>

<template>
  <Page auto-content-height>
    <Grid>
      <template #toolbar-tools>
        <a-button type="primary" @click="handleAdd">新增</a-button>
      </template>
      <template #action="{ row }">
        <a-button type="link" size="small" @click="handleEdit(row)">编辑</a-button>
        <a-button type="link" size="small" danger @click="handleDelete(row)">删除</a-button>
      </template>
    </Grid>
  </Page>
</template>
