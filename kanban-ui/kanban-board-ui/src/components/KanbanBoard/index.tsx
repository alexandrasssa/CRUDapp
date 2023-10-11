import React, { useEffect } from 'react';

import { DragDropContext, DropResult } from 'react-beautiful-dnd';

import ICard from '../../interfaces/ICard';
import IStatus from '../../interfaces/IStatus';
import Column from '../ColumnTask';
import Modal from '../Modal';
import { useModalTask } from '../../hooks/useModalTask';
import { useAppDispatch, useAppSelector } from '../../hooks/useRedux';
import { Container, ContainerFeatures, StatusesColumnsContainer } from './styles';
import { fetchCards, sendUpdateCardToBackend, setCards, updateOneCard } from '../../store/tasks/slices/cards.slice';
import { ButtonAddCardTask } from '../ButtonAddCardTask';
import { ButtonAddCardFeature } from '../ButtonAddCardFeature';
import ColumnFeature from '../ColumnFeature';
import { fetchFeatureCards } from '../../store/features/feature-card';
import { useModalFeature } from '../../hooks/useModalFeature';
import ModalFeature from '../ModalFeature';

const KanbanBoard: React.FC = () => {

  const cards = useAppSelector((state => state.cards.cards));
  const { columns } = useAppSelector((state => state.columns));
  const featureCards = useAppSelector((state => state.featureCards.featureCards));
  const { visible: visibleTaskModal } = useModalTask();
  const { visible: visibleFeatureModal } = useModalFeature();

  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(fetchFeatureCards());
  }, [dispatch]);

  useEffect(() => {
    dispatch(fetchCards());
  }, [dispatch]);

  const onDragEnd = (result: DropResult) => {
    const { destination, source, draggableId } = result;

    if (!destination) return;

    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) return;

    const updatedCards: ICard[] = cards.map(card => {
      if (card.id.toString() === draggableId) {

        const status: IStatus = destination.droppableId as IStatus;

        return {
          ...card,
          status
        }
      } else return card;
    })

    const updatedCard: ICard = updatedCards.find(card => card.id.toString() === draggableId.toString()) as ICard

    dispatch(updateOneCard(updatedCard))
    dispatch(sendUpdateCardToBackend(updatedCard))
    dispatch(setCards(updatedCards))
  }
  const onFeatureDragEnd = (result: DropResult) => {
    return
  }
  const grouped = cards.reduce((group: {[key: string]: ICard[]}, item) => {
    if (!group[item.status]) {
      group[item.status] = [];
    }
    group[item.status].push(item)
    return group;
  }, {})

  return (
    <>
      <Container>
        <ButtonAddCardTask />

        <StatusesColumnsContainer>
          <DragDropContext onDragEnd={onDragEnd}>
            {columns.map((column, index) => {

              const cardsArray: ICard[] = [];
              const groupedById = grouped[column.id] || []
              const cardsIds = groupedById.map(i => i.id) || []

              cardsIds.forEach(cardId => {
                const foundedCard = cards.find(card => card.id.toString() === cardId.toString());
                if (foundedCard) cardsArray.push(foundedCard);
              })
              return (
                <Column
                  key={column.id}
                  index={index}
                  status={column.id}
                  cards={cardsArray}
                />
              )
            })}
          </DragDropContext>
        </StatusesColumnsContainer>
      </Container>
      <ButtonAddCardFeature />
      <ContainerFeatures>
        <DragDropContext onDragEnd={onFeatureDragEnd}>
          <ColumnFeature
            key={'ColumnFeatureKey'}
            index={0}
            cards={featureCards}
          />
        </DragDropContext>
      </ContainerFeatures>
      <Modal visible={visibleTaskModal} />
      <ModalFeature visible={visibleFeatureModal} />
    </>
  )
}

export default KanbanBoard;