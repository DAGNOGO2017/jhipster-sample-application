import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDon } from 'app/shared/model/don.model';
import { DonService } from './don.service';

@Component({
    selector: 'jhi-don-delete-dialog',
    templateUrl: './don-delete-dialog.component.html'
})
export class DonDeleteDialogComponent {
    don: IDon;

    constructor(protected donService: DonService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.donService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'donListModification',
                content: 'Deleted an don'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-don-delete-popup',
    template: ''
})
export class DonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ don }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DonDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.don = don;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
