import React, { Component } from 'react';
import LoadingSpinner from '../Spinner';
import { Button } from '../Modal/styles';

interface FeatureButtonProps {
    onSubmit: (callback: () => void) => void;
}

class FeatureButtonSave extends Component<FeatureButtonProps> {
    state = {
      loading: false,
    };
  
    handleClick = () => {
      this.setState({ loading: true });
  
      this.props.onSubmit(() => {
        this.setState({ loading: false });
      });
    };
  
    render() {
      return (
        <div>
          <Button onClick={this.handleClick} disabled={this.state.loading}>
            {this.state.loading ? (
              <LoadingSpinner />
            ) : (
              'Save'
            )}
          </Button>
        </div>
      );
    }
  }

export default FeatureButtonSave;
