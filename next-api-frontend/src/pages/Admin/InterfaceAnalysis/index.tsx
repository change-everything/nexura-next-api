import { listTopInvokeInterfaceInfoUsingGet } from '@/services/next-api/analysisController';
import { PageContainer } from '@ant-design/pro-components';
import ReactECharts from 'echarts-for-react';
import React, { useEffect, useState } from 'react';

const Index: React.FC = () => {
  const [data, setData] = useState<API.InterfaceInfoVO[]>([]);

  const loadData = async () => {
    const res = await listTopInvokeInterfaceInfoUsingGet();
    if (res.data) {
      setData(res.data);
    }
  };

  useEffect(() => {
    loadData();
    console.log('data----->', data);
  }, []);

  const chartData = data.map((item) => {
    return {
      value: item.totalNum,
      name: item.name,
    };
  });

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b} 调用次数 : {c} 次 (占比：{d}%)'
    },
    legend: {
      top: 'bottom',
    },
    toolbox: {
      show: true,
      feature: {
        mark: { show: true },
        dataView: { show: true, readOnly: false },
        restore: { show: true },
        saveAsImage: { show: true },
      },
    },
    series: [
      {
        name: '接口调用分析',
        type: 'pie',
        radius: [50, 250],
        center: ['50%', '50%'],
        roseType: 'area',
        itemStyle: {
          borderRadius: 8,
        },
        emphasis: {
          label: {
            show: true
          }
        },
        data: chartData,
      },
    ],
  };

  return (
    <PageContainer title="接口调用分析">
      <ReactECharts option={option} style={{ height: '80vh' }} />
    </PageContainer>
  );
};

export default Index;
