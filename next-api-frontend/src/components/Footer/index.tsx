import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright={'2024 peiYP'}
      links={[
        {
          key: 'peiYP',
          title: 'peiYP',
          href: 'https://github.com/change-everything',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/change-everything',
          blankTarget: true,
        },
        {
          key: '津ICP备2024012731号',
          title: '津ICP备2024012731号',
          href: 'https://beian.miit.gov.cn/',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
