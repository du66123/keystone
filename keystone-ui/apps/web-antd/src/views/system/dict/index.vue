<script setup lang="ts">
import { useVbenVxeGrid } from '#/adapter/vxe-table';
import { deleteDictType, getDictTypeList } from '#/api/sys/dict';
import { Page } from '@vben/common-ui';
import { message, Modal } from 'ant-design-vue';

const [Grid, gridApi] = useVbenVxeGrid({
  formOptions: {
    collapsed: false,
    schema: [
      {
        component: 'Input',
        fieldName: 'dictName',
        label: '字典名称',
      },
      {
        component: 'Input',
        fieldName: 'dictType',
        label: '字典类型',
      },
      {
        component: 'Select',
        fieldName: 'status',
        label: '字典状态',
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
    rowConfig: { keyField: 'dictId', isHover: true },
    columns: [
      { type: 'seq', width: 50 },
      { field: 'dictId', title: '字典编号', width: 80 },
      { field: 'dictName', title: '字典名称' },
      { field: 'dictType', title: '字典类型' },
      { field: 'status', title: '状态', width: 80 },
      { field: 'remark', title: '备注' },
      { field: 'createTime', title: '创建时间' },
      { title: '操作', field: 'action', width: 150, fixed: 'right' }
    ],
    proxyConfig: {
      ajax: {
        query: async ({ params }) => {
          const { items, total } = await getDictTypeList(params) as any;
          return { items: items || [], total: total || 0 };
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
  message.info('新增字典弹窗待实现');
}

function handleEdit(row: any) {
  message.info(`编辑字典: ${row.dictName}`);
}

function handleDelete(row: any) {
  Modal.confirm({
    title: '确认删除',
    content: `是否确认删除字典类型 "${row.dictName}"？`,
    onOk: async () => {
      await deleteDictType(row.dictId);
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
