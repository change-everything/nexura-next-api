import CodeEditor from '@uiw/react-textarea-code-editor';
import React from 'react';

export default class CodeEdit extends React.Component<{ value: any; onChange: any }> {
  render() {
    let { value, onChange } = this.props;

    return (
      <CodeEditor
        value={value}
        language="json"
        onChange={onChange}
        padding={15}
        style={{
          backgroundColor: '#f5f5f5',
          fontFamily:
            'ui-monospace,SFMono-Regular,SF Mono,Consolas,Liberation Mono,Menlo,monospace',
        }}
      />
    );
  }
}
