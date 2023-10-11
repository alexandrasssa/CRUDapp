import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

import ICardFeature from "../../interfaces/ICardFeature";
import axios from "axios";

interface FeatureCardsSliceState {
  featureCards: ICardFeature[],
  status: string,
  error: string | undefined
}

const initialState : FeatureCardsSliceState = {
  featureCards: [],
  status: "idle",
  error: undefined ,
};

export const fetchFeatureCards = createAsyncThunk(
  "featureCards/fetchFeatureCards",
  async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/feature") ;
      return response.data as ICardFeature[]; 
    } catch (error) {
      throw error;
    }
  }
);

export const featureCardsSlice = createSlice({
  name: 'featureCards',
  initialState,
  reducers: {
    setFeatureCards: (state, action) => {
      state.featureCards = action.payload
    },
    addFeatureCard: (state, action) => {
      const card = action.payload as ICardFeature

      state.featureCards = [...state.featureCards, card]
    },
    updateOneFeatureCard: (state, action) => {
      const cardId = action.payload.id;

      const updatedCards = state.featureCards.map(card => {
        if (card.id === cardId) return action.payload;
        else return card;
      })

      state.featureCards = updatedCards;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchFeatureCards.pending, (state) => {
        state.status = "loading";
      })
      .addCase(fetchFeatureCards.fulfilled, (state, action) => {
        state.status = "succeeded";
        state.featureCards = action.payload as ICardFeature[];
      })
      .addCase(fetchFeatureCards.rejected, (state, action) => {
        state.status = "failed";
        state.error = action.error ? action.error.message as string : "An error occurred";
      });
  }
})

export const { setFeatureCards, updateOneFeatureCard, addFeatureCard } = featureCardsSlice.actions;

export default featureCardsSlice.reducer;