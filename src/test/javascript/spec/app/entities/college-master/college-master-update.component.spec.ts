import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { CollegeMasterUpdateComponent } from 'app/entities/college-master/college-master-update.component';
import { CollegeMasterService } from 'app/entities/college-master/college-master.service';
import { CollegeMaster } from 'app/shared/model/college-master.model';

describe('Component Tests', () => {
  describe('CollegeMaster Management Update Component', () => {
    let comp: CollegeMasterUpdateComponent;
    let fixture: ComponentFixture<CollegeMasterUpdateComponent>;
    let service: CollegeMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [CollegeMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CollegeMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CollegeMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CollegeMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollegeMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CollegeMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
