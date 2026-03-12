import { defineConfig } from '@vben/vite-config';

export default defineConfig(async () => {
  return {
    application: {},
    vite: {
      server: {
        proxy: {
          '/api': {
            changeOrigin: true,
            rewrite: (path) => path.replace(/^\/api/, ''),
            // 指向 Keystone 后端接口地址
            target: 'http://localhost:8080/api',
            ws: true,
          },
        },
      },
    },
  };
});
