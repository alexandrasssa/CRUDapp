import React, { useContext, useEffect, useState } from 'react';
import { ThemeContext } from 'styled-components';

import CloseIcon from '../../assets/close.png';
import getCategoryBackgroundColor from '../../helpers/getCategoryBackgroundColor';
import { useModalFeature } from '../../hooks/useModalFeature';
import ICategory from '../../interfaces/ICategory';
import {
  Container,
  Input,
  ModalContent,
  MultilineInput,
  CategoriesContainer,
  LabelContainer,
  ErrorMessage
} from './styles';
import ICard from '../../interfaces/ICard';
import ButtonSaveCardFeature from '../ButtonSaveCardFeature';
import axios from 'axios';

interface ModalProps {
  visible: boolean;
}

const ModalFeature: React.FC<ModalProps> = ({ visible }) => {
  const { toggleFeatureVisibility, selectedCard } = useModalFeature();
  const theme = useContext(ThemeContext);

  const [title, setTitle] = useState<string | undefined>(selectedCard?.title);
  const [description, setDescription] = useState<string | undefined>(selectedCard?.description);
  const [cardCategory, setCardCategory] = useState<ICategory>(selectedCard?.category || ICategory.FEATURE);
  const [tasks, setTasks] = useState<ICard[] | undefined>(selectedCard?.tasks || []);
  const [errorMessage, setErrorMessage] = useState<string | undefined>();

  useEffect(() => {
    setTitle(selectedCard?.title);
    setDescription(selectedCard?.description);
    setCardCategory(selectedCard?.category || ICategory.FEATURE);
    setTasks(selectedCard?.tasks);
  }, [selectedCard, visible])

  const handleCloseModal = () => {
    toggleFeatureVisibility(undefined);
    setTitle(undefined);
    setDescription(undefined);
    setCardCategory(ICategory.FEATURE);
    setErrorMessage(undefined);
  }

  const handleSubmit = (callback: any) => {
    const payload = {
      title: title,
      description: description,
      category: cardCategory,
      status: 'TO_DO',
      board: {
        id: '1',
      },
    };
    if (selectedCard?.id) {
      axios.put('http://localhost:8080/api/feature/' + selectedCard.id, payload)
        .then(() => {
          alert('Updated successfully')
          window.location.href = 'http://localhost:3000/kanban-board';
        })
        .catch(() => {
          alert('Update action failed...')
          window.location.href = 'http://localhost:3000/kanban-board';
        })
        .finally(() => {
          callback();
        });
    } else {
      axios.post('http://localhost:8080/api/feature/', payload)
        .then(() => {
          alert('Created successfully')
          window.location.href = 'http://localhost:3000/kanban-board';
        })
        .catch(() => {
          alert('Create action failed...')
          window.location.href = 'http://localhost:3000/kanban-board';
        })
        .finally(() => {
          callback();
        });
    }
  };

  if (!visible) return null;
  return (
    <div>
      <Container>
        <ModalContent>
          <img src={CloseIcon} alt="Gray X icon" onClick={handleCloseModal} />

          <h3>Feature title</h3>
          <Input value={title} onChange={(e) => setTitle(e.target.value)} minLength={1} maxLength={50} containsError={!!errorMessage} />
          {errorMessage && (
            <ErrorMessage>{errorMessage}</ErrorMessage>
          )}

          <h3>Description</h3>
          <MultilineInput
            aria-multiline
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            maxLength={300}
          />
          <h3 style={{ paddingTop: '10px', paddingBottom: '20px' }}>Feature status: {selectedCard?.status}</h3>
          <CategoriesContainer>
            {Object.values(ICategory).map(category => (
              <LabelContainer color={() => getCategoryBackgroundColor(theme, category)}>
                <label>
                  <input
                    type='radio'
                    name={category}
                    value={category}
                    checked={cardCategory === category}
                    onChange={(e) => setCardCategory(e.currentTarget.value as ICategory)}
                  />
                  <i>{category}</i>
                </label>
              </LabelContainer>
            ))}
          </CategoriesContainer>
          <ButtonSaveCardFeature onSubmit={handleSubmit}></ButtonSaveCardFeature>
          <h3 style={{ paddingTop: '20px', paddingBottom: '10px' }}>Tasks</h3>
          {tasks?.map((task) => (
            <li style={{ paddingTop: '10px' }}>{task.title}</li>
          ))}
        </ModalContent>
      </Container>
    </div>
  )
}

export default ModalFeature;