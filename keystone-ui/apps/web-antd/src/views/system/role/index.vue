<script setup lang="ts">
import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteRoleApi, getRoleListApi } from '#/api/sys/role';
import { Page } from '@vben/common-ui';
import { message, Modal } from 'ant-design-vue';

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    collapsed: false,
    schema: [
      {
        component: 'Input',
        fieldName: 'roleName',
        label: '角色名称',
      },
      {
        component: 'Input',
        fieldName: 'roleKey',
        label: '权限字符',
      },
      {
        component: 'Select',
        fieldName: 'status',
        label: '角色状态',
        componentProps: {
          options: [
            { label: '正常', value: '0' },
            { label: '停用', value: '1' },
          ],
        },
      },
    ],
    showCollapseButton: false,
    submitButtonOptions: { content: '查询' },
  },
  gridOptions: {
    columns: [
      { type: 'seq', width: 50 },
      { field: 'roleId', title: '角色编号', width: 80 },
      { field: 'roleName', title: '角色名称' },
      { field: 'roleKey', title: '权限字符' },
      { field: 'roleSort', title: '显示顺序', width: 80 },
      { field: 'status', title: '状态', width: 80 },
      { field: 'createTime', title: '创建时间' },
      { title: '操作', field: 'action', width: 150, fixed: 'right' }
    ],
    proxyConfig: {
      ajax: {
        query: async ({ params }) => {
          const data = await getRoleListApi(params);
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
  message.info('新增角色弹窗待实现');
}

function handleEdit(row: any) {
  message.info(`编辑角色: ${row.roleName}`);
}

function handleDelete(row: any) {
  Modal.confirm({
    title: '确认删除',
    content: `是否确认删除角色 "${row.roleName}"？`,
    onOk: async () => {
      await deleteRoleApi([row.roleId]);
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
