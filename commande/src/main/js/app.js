import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom";
import halRestClient from "./halRestClient";
import restClient from "./restClient";


const App = () => {

	const [commandes, setCommandes] = useState([]);
	const [produits, setProduits] = useState([{ id:1, nom:"dummy (réponse du serveur pas reçue)" }]);

    // Similar to componentDidMount and componentDidUpdate
    useEffect(() => {
		updateAll()
    }, []); // sinon appelé en boucle car redéclenché par setCommandes()

    const updateAll = () => {
        /*
		halRestClient({method: 'GET', path: '/api/data-rest/commandes'}).done(response => {
			setCommandes(response.entity._embedded.commandes);
		});
		*/
		restClient({method: 'GET', path: '/api/commandes'}).done(response => {
			setCommandes(response.entity);
		});
		halRestClient({method: 'GET', path: '/api/data-rest/produitEnStocks'}).done(response => {
			setProduits(response.entity._embedded.produitEnStocks);
		});
    }

    const produitCpts = produits.map(produit =>
        <NouvelleCommandeDeProduit key={produit.id} produit={produit} refreshCommande={updateAll}/>
    )

    return (
      <>
        <CommandeList commandes={commandes} refreshCommande={updateAll}/>

        <p/>
        <p/>

        Produits :
        <p/>
        { produitCpts }
      </>
    )
}

/**
 * or with data rest (HAL) key={commande._links.self.href}
 */
const CommandeList = ({ commandes, refreshCommande }) => {
    const commandeCpts = commandes.map(commande =>
        <Commande key={commande.id} commande={commande} refreshCommande={refreshCommande}/>
    )
    return (
        <table>
            <tbody>
                <tr>
                    <th>ID</th>
                    <th>produitId</th>
                    <th>quantite</th>
                    <th>quantiteDisponibleStockConnu</th>
                    <th>status</th>
                    <th></th>
                    <th></th>
                </tr>

                { commandeCpts }

            </tbody>
        </table>
    )
}

const Commande = ({ commande, refreshCommande }) => {
    const [state, setState] = useState(commande);
    const update = () => {
        /*
        halRestClient({method: 'PUT', path: '/api/data-rest/commandes' + commande.id,
            entity: state, headers: {'Content-Type': 'application/json'}}).done(response => {
            setState(response.entity._embedded.commande);
        });
        restClient({method: 'PUT', path: '/api/commandes/' + state.id,
            entity: state, headers: {'Content-Type': 'application/json'}}).done(response => {
            refreshCommande(response.entity._embedded.commande);
        });
        */
        restClient({method: 'PUT', path: '/api/commandes/' + state.id,
            entity: state, headers: {'Content-Type': 'application/json'}}).done(response => {
            refreshCommande(response.entity);
        });
    }
    const validate = () => {
        restClient({method: 'POST', path: '/api/commandes/' + state.id + "/validate",
            entity: state, headers: {'Content-Type': 'application/json'}}).done(response => {
            refreshCommande(response.entity);
        });
    }
    return (
        <tr>
            <td>{commande.id}</td>
            <td>{commande.produitId}</td>
            <td><input type="number" value={state.quantite}
                onChange={(e) => setState({...state, "quantite" : e.target.value})}/></td>
            <td>{commande.quantiteDisponibleStockConnu}</td>
            <td>{commande.status}</td>

            {/*
            <td><input type="number" value={state.produitId}
                onChange={(e) => setState({...state, "produitId" : e.target.value})}/></td>
            <td><input type="number" value={state.quantiteDisponibleStockConnu}
                onChange={(e) => setState({...state, "quantiteDisponibleStockConnu" : e.target.value})}/></td>
            <td><select value={state.status}
                onChange={(e) => setState({...state, "status" : e.target.value})}>
                  <option value="created"></option>
                  <option value="validated"></option>
                </select></td>
            */}

            <td><button onClick={update}>Enregistrer</button></td>
            <td><button onClick={validate}>Valider</button></td>
        </tr>
    )
}



const NouvelleCommandeDeProduit = ({ produit, refreshCommande }) => {
    const create = () => {
        /*
        halRestClient({method: 'POST', path: '/api/data-rest/commandes',
            entity: state, headers: {'Content-Type': 'application/json'}}).done(response => {
            setState(response.entity._embedded.commande);
        });
        */
        const commande = {
            produitId : produit.id,
            quantite : 1
        }
        restClient({method: 'POST', path: '/api/commandes',
            entity: commande, headers: {'Content-Type': 'application/json'}}).done(response => {
            refreshCommande(response.entity);
        });
    }
    return (
        <div>
            {produit.id} {produit.nom}
            <br/>
            {produit.description || '-'}
            <br/>
            <button onClick={create}>Nouvelle commande de ce produit</button>
            <p/>
        </div>
    )
}

const ReadOnlyCommande = ({ commande }) => {
    return (
        <tr>
          <form>
            <td>{commande.id}</td>
            <td>{commande.produitId}</td>
            <td>{commande.quantite}</td>
            <td>{commande.quantiteDisponibleStockConnu}</td>
            <td>{commande.status}</td>
          </form>
        </tr>
    )
}

ReactDOM.render(
	<App />,
	document.getElementById('root')
)