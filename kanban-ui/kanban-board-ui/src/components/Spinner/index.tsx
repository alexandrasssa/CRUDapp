import { RingLoader } from 'react-spinners';

const LoadingSpinner = () => {
  return (
    <RingLoader
    size={15}
    color={'#123abc'}
    loading={true}
    />
  );
};

export default LoadingSpinner;