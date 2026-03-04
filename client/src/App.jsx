import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import UserProvider from './context/UserProvider';
import NavBar from './components/NavBar/NavBar';
import MainView from './views/MainView/MainView';
import LoginView from './views/LoginView/LoginView';
import LogoutView from './views/LogoutView';
import RegisterView from './views/RegisterView/RegisterView';
import CampaignsView from './views/CampaignsView/CampaignsView';
import CampaignDetailsView from './views/CampaignsView/CampaignDetailsView';
import CharactersView from './views/CharactersView/CharactersView';
import PartiesView from './views/PartiesView/PartiesView';
import PartyDetailsView from './views/PartiesView/PartyDetailsView';
import CreatePartyView from './views/PartiesView/CreatePartyView';
import PlotsView from './views/PlotsView/PlotsView';
import CreatePlotView from './views/PlotsView/CreatePlotView';
import CreateCampaignView from './views/CampaignsView/CreateCampaignView';
import EditCampaignView from './views/CampaignsView/EditCampaignView';
import CreateCharacterView from './views/CharactersView/CreateCharacterView';
import CharacterDetailsView from './views/CharactersView/CharacterDetailsView';
import EditCharacterView from './views/CharactersView/EditCharacterView';


export default function App() {

  return (
    <div id="crawler-companion-app">
      <BrowserRouter>
        <UserProvider>
        <NavBar />
        <main>
          <Routes>
            <Route path="/" element={<MainView />} />
            <Route path="/login" element={<LoginView />} />
            <Route path="/logout" element={<LogoutView />} />
            <Route path="/register" element={<RegisterView />} />
            <Route path="/campaigns" element={<CampaignsView />} />
            <Route path="/campaigns/:campaignId" element={<CampaignDetailsView />} />
            <Route path="/campaigns/new" element={<ProtectedRoute requireAdmin={true}><CreateCampaignView /></ProtectedRoute>} />
            <Route path="/campaigns/:campaignId/edit" element={<ProtectedRoute requireAdmin={true}><EditCampaignView /></ProtectedRoute>} />
            <Route path="/characters" element={<CharactersView />} />
            <Route path="/characters/:characterId" element={<CharacterDetailsView />} />
            <Route path="/characters/new" element={<ProtectedRoute><CreateCharacterView /></ProtectedRoute>} />
            <Route path="/characters/:characterId/edit" element={<ProtectedRoute requireAdmin={true}><EditCharacterView /></ProtectedRoute>} />
            <Route path="/parties" element={<PartiesView />} />
            <Route path="/parties/:partyId" element={<PartyDetailsView />} />
            <Route path="/parties/new" element={<ProtectedRoute requireAdmin={true}><CreatePartyView /></ProtectedRoute>} />
            <Route path="/plots" element={<PlotsView />} />
            <Route path="/plots/new" element={<ProtectedRoute><CreatePlotView /></ProtectedRoute>} />
          </Routes>
        </main>
        </UserProvider>
      </BrowserRouter>
    </div>
  );
}
