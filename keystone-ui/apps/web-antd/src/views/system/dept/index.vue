<script setup lang="ts">
import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteDept, getDeptList } from '#/api/sys/dept';
import { Page } from '@vben/common-ui';
import { message, Modal } from 'ant-design-vue';

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    collapsed: false,
    schema: [
      {
        component: 'Input',
        fieldName: 'deptName',
        label: '部门名称',
      },
      {
        component: 'Select',
        fieldName: 'status',
        label: '状态',
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
    rowConfig: { keyField: 'deptId', isHover: true },
    treeConfig: { transform: true, rowField: 'deptId', parentField: 'parentId', expandAll: true },
    columns: [
      { field: 'deptName', title: '部门名称', treeNode: true },
      { field: 'orderNum', title: '排序', width: 80 },
      { field: 'status', title: '状态', width: 80 },
      { field: 'createTime', title: '创建时间' },
      { title: '操作', field: 'action', width: 150, fixed: 'right' }
    ],
    proxyConfig: {
      ajax: {
        query: async ({ params }) => {
          const data = await getDeptList(params);
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
  message.info('新增部门弹窗待实现');
}

function handleEdit(row: any) {
  message.info(`编辑部门: ${row.deptName}`);
}

function handleDelete(row: any) {
  Modal.confirm({
    title: '确认删除',
    content: `是否确认删除部门 "${row.deptName}"？`,
    onOk: async () => {
      await deleteDept(row.deptId);
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
