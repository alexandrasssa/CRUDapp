import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

import ICard from "../../../interfaces/ICard";
import axios from "axios";

interface CardsSliceState {
  cards: ICard[],
  status: string,
  error: string | undefined
}

const initialState: CardsSliceState = {
  cards: [],
  status: "idle",
  error: undefined
}

export const fetchCards = createAsyncThunk(
  "cards/fetchCards",
  async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/card") ;
      return response.data as ICard[]; 
    } catch (error) {
      throw error;
    }
  }
);
export const sendUpdateCardToBackend = createAsyncThunk(
  "cards/sendUpdateCardToBackend",
  async (card: any) => {
    try {
      const response = await axios.put("http://localhost:8080/api/card/" + card.id, card);
      alert('Updated successfully')
      window.location.href = 'http://localhost:3000/kanban-board';

      return response.data as ICard;
    } catch (error) {
      alert('Action failed')
      window.location.href = 'http://localhost:3000/kanban-board';
      throw error;
    }
  }
);

export const sendSaveCardToBackend = createAsyncThunk(
  "cards/sendSaveCardToBackend",
  async (card: ICard) => {
    try {
      const response = await axios.post("http://localhost:8080/api/card/", card);
      alert('Saved successfully')
      window.location.href = 'http://localhost:3000/kanban-board';

      return response.data as ICard;
    } catch (error) {
      alert('Action failed')
      window.location.href = 'http://localhost:3000/kanban-board';
      throw error;
    }
  }
);

export const cardsSlice = createSlice({
  name: 'cards',
  initialState,
  reducers: {
    setCards: (state, action) => {
      state.cards = action.payload
    },
    addCard: (state, action) => {
      const card = action.payload
      state.cards = [...state.cards, card]
    },
    updateOneCard: (state, action) => {
      const cardId = action.payload.id;

      const updatedCards = state.cards.map(card => {
        if (card.id === cardId) return action.payload;
        else return card;
      })

      state.cards = updatedCards;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCards.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchCards.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.cards = action.payload as ICard[];
      })
      .addCase(fetchCards.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error ? action.error.message as string : "An error occurred";
      });
  }
})

export const { setCards, updateOneCard, addCard } = cardsSlice.actions;

export default cardsSlice.reducer;