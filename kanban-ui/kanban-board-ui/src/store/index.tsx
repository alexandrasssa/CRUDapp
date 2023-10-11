import { configureStore } from '@reduxjs/toolkit'

import columnsReducer from './tasks/slices/columns.slice';
import cardsReducer from './tasks/slices/cards.slice';
import featureCardsReducer from './features/feature-card';

const store = configureStore({
  reducer: {
    columns: columnsReducer,
    cards: cardsReducer,
    featureCards: featureCardsReducer
  }
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export default store
