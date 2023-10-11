import { useState, useEffect } from 'react';
import axios from 'axios';
import ICardFeature from '../../interfaces/ICardFeature';

interface DropdownProps {
  preselected: string,
  onSelectChange: (value: string) => void; 
}

function DropdownComponent({ onSelectChange, preselected }: DropdownProps) {
  const [data, setData] = useState<ICardFeature[]>([]); 
  const [selectedOption, setSelectedOption] = useState('');

  useEffect(() => {
    axios.get<ICardFeature[]>('http://localhost:8080/api/feature')
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);

  
  const handleDropdownChange = (event: any) => {
    const selectedValue = event.target.value;
    setSelectedOption(selectedValue);

    onSelectChange(selectedValue);
  };

  return (
    <div>
      <h2 style={{ paddingBottom: '20px' }}>Currently assigned to: {data.find(f => f.id === preselected)?.title}</h2>
      <h2>Select feature if want to change:</h2>
      <select value={selectedOption} onChange={handleDropdownChange} defaultValue={preselected}>
        <option value="">Select an option</option>
        {data.map((cardFeature, index) => (
          <option key={index} value={cardFeature.id}>
            {cardFeature.title}
          </option>
        ))}
      </select>
    </div>
  );
}

export default DropdownComponent;