import {Injectable} from '@angular/core';
import {Http, Response, URLSearchParams} from '@angular/http'
import {JhiDateUtils} from 'ng-jhipster';
import {SERVER_API_URL} from '../app.constants';
import {Observable} from 'rxjs/Rx';
import {ResponseWrapper} from '../shared';
import {LancamentoFinapp, Tipo} from '../entities/lancamento/lancamento-finapp.model';

@Injectable()
export class DashboardService {

    private resourceUrl = SERVER_API_URL + 'api/lancamentos-dashboard-by-tipo';
    private resourceUrlUpdateLancamento = SERVER_API_URL + 'api/lancamentos';
    private resourceUrlContas = SERVER_API_URL + 'api/contas';

  constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    getDespesas(dataSelecionada: any): Observable<ResponseWrapper> {
          const params: URLSearchParams = new URLSearchParams();
          params.set('tipo', 'DESPESA');
          params.set('dataSelecionada', dataSelecionada.toISOString().split('T')[0]);
          return this.http.get(this.resourceUrl, { search: params })
              .map((res: Response) => this.convertResponse(res));
      }

    getReceitas(dataSelecionada: any): Observable<ResponseWrapper> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('tipo', 'RECEITA');
        params.set('dataSelecionada', dataSelecionada.toISOString().split('T')[0]);
        return this.http.get(this.resourceUrl, { search: params })
            .map((res: Response) => this.convertResponse(res));
    }

    getListaCategoriaAgrupado(dataSelecionada: any, tipo: string): Observable<ResponseWrapper> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('tipo', tipo);
        params.set('dataSelecionada', dataSelecionada.toISOString().split('T')[0]);
        return this.http.get(this.resourceUrlUpdateLancamento + '/dashboard/grupo-mes/categoria', { search: params })
            .map((res: Response) => this.convertResponse(res));
    }

    getAllByDateGroupByTipo(dataSelecionada: any): Observable<ResponseWrapper> {
        const params: URLSearchParams = new URLSearchParams();
        params.set('dataSelecionada', dataSelecionada.toISOString().split('T')[0]);
        return this.http.get(this.resourceUrlUpdateLancamento + '/dashboard/grupo-mes/tipo', { search: params })
            .map((res: Response) => this.convertResponse(res));
    }

    getAllContas(): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrlContas + '/dashboard/saldos')
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.data = this.dateUtils
            .convertLocalDateFromServer(entity.data);
    }

    update(lancamento: LancamentoFinapp): Observable<LancamentoFinapp> {
        return this.http.put(this.resourceUrlUpdateLancamento, lancamento).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
}
