import React, { useEffect, useState } from 'react';

import CloseIcon from '../../assets/close.png';
import { useModalTask } from '../../hooks/useModalTask';
import { useAppDispatch } from '../../hooks/useRedux';
import IStatus from '../../interfaces/IStatus';
import { addCard, sendSaveCardToBackend, sendUpdateCardToBackend, updateOneCard , } from '../../store/tasks/slices/cards.slice';
import { 
  Container, 
  Input, 
  Button,
  ModalContent, 
  MultilineInput, 
  ErrorMessage
} from './styles';
import DropdownComponent from '../async/async-select';
import ICard from '../../interfaces/ICard';

interface ModalProps{
  visible: boolean;
}

const Modal: React.FC<ModalProps> = ({visible}) => {
  const { toggleVisibility, selectedCard } = useModalTask();

  const [title, setTitle] = useState<string | undefined>(selectedCard?.title);
  const [description, setDescription] = useState<string>(selectedCard?.description || '');
  const [errorMessage, setErrorMessage] = useState<string | undefined>();

  const dispatch = useAppDispatch();

  useEffect(() => {
    setTitle(selectedCard?.title);
    setDescription(selectedCard?.description || '');
    setPreselectedValue(selectedCard?.featureId || '')
  }, [selectedCard, visible])

  const handleSave = () => {

    if (!title){
      setErrorMessage("The title field can´t be empty!")
      return;
    }

    setErrorMessage(undefined);

    if(!selectedCard?.id){
      const newCard : ICard = {
        id: '',
        title,
        description,
        featureId: selectedValue,
        status: IStatus.BACKLOG,
      }
      dispatch(addCard(newCard))
      dispatch(sendSaveCardToBackend(newCard))
      toggleVisibility(undefined);
    }

    const updatedCard = {
      id: selectedCard?.id || '',
      featureId: selectedValue,
      title: title,
      description: description,
    }

    dispatch(updateOneCard(updatedCard))
    dispatch(sendUpdateCardToBackend(updatedCard))
    toggleVisibility(undefined);
  }

  const handleCloseModal = () => {
    toggleVisibility(undefined);
    setTitle(undefined);
    setDescription('');
    setErrorMessage(undefined);
  }
  
  const [preselectedValue, setPreselectedValue] = useState(selectedCard?.featureId || '');
  const [selectedValue, setSelectedValue] = useState('');
  const handleSelectChange = (value: any) => {
    setSelectedValue(value);
  };


  if (!visible) return null;
  return (
    <Container>
      <ModalContent>
        <img src={CloseIcon} alt="Gray X icon" onClick={handleCloseModal}/>

        <h3>Title</h3>
        <Input value={title} onChange={(e) => setTitle(e.target.value)} minLength={1} maxLength={50} containsError={!!errorMessage}/>
        {errorMessage && (
          <ErrorMessage>{errorMessage}</ErrorMessage>
        )}

        <h3>Descriptionnn</h3>
        <MultilineInput 
          aria-multiline 
          value={description} 
          onChange={(e) => setDescription(e.target.value)} 
          maxLength={300}
        />
        <DropdownComponent preselected={preselectedValue} onSelectChange={handleSelectChange}/>
        <Button type='button' onClick={handleSave}>{selectedCard ? 'Save Changes' : 'Add card to Backlog'}</Button>

      </ModalContent>
    </Container>
  )
}

export default Modal;